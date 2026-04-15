# Deploy lên Minikube

## 1. Khởi động minikube
```bash
minikube start --driver=docker --memory=4096 --cpus=2
```

## 2. Trỏ Docker CLI vào daemon trong minikube
Để build image trực tiếp trong cluster (không cần push registry).

**PowerShell:**
```powershell
& minikube -p minikube docker-env --shell powershell | Invoke-Expression
```

**Git Bash / Linux / Mac:**
```bash
eval $(minikube docker-env)
```

## 3. Build 4 image
Chạy ở thư mục gốc `qly-sanbong-mini-prj`:
```bash
docker build -t user-service:latest     ./user-service
docker build -t supplier-service:latest ./supplier-service
docker build -t product-service:latest  ./product-service
docker build -t import-service:latest   ./import-service
```

## 4. Apply manifest
```bash
kubectl apply -f k8s/
```

## 5. Kiểm tra
```bash
kubectl get pods -n qlysanbong
kubectl get svc  -n qlysanbong
kubectl logs -n qlysanbong deploy/user-service
```

## 6. Truy cập service
```bash
minikube service user-service     -n qlysanbong
minikube service supplier-service -n qlysanbong
minikube service product-service  -n qlysanbong
minikube service import-service   -n qlysanbong
```
Hoặc xem URL trực tiếp:
```bash
minikube service user-service -n qlysanbong --url
```

NodePort đã set:
| Service           | Port nội bộ | NodePort |
|-------------------|-------------|----------|
| user-service      | 8081        | 30081    |
| supplier-service  | 8082        | 30082    |
| product-service   | 8083        | 30083    |
| import-service    | 8084        | 30084    |

## 7. Cập nhật code → rebuild → rollout
```bash
docker build -t user-service:latest ./user-service
kubectl rollout restart deploy/user-service -n qlysanbong
```

## 8. Gỡ toàn bộ
```bash
kubectl delete namespace qlysanbong
```

## Ghi chú
- MySQL chạy 1 pod duy nhất, dữ liệu lưu vào PVC `mysql-pvc` → giữ lại khi pod restart.
- Mật khẩu root MySQL nằm trong Secret `mysql-secret` (đang để `252204nhat` cho đồng bộ với code dev — production nên đổi).
- 4 DB (`user_db`, `supplier_db`, `product_db`, `import_db`) tự tạo nhờ `createDatabaseIfNotExist=true`.
- Spring Boot tự nạp `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` từ env, override `application.properties`.
- `import-service` gọi 2 service kia qua DNS nội bộ K8s: `http://supplier-service:8082`, `http://product-service:8083`.
