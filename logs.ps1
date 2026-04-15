param(
    [string]$service = "all"
)

function Watch-Service {
    param([string]$name, [string]$color)

    kubectl logs -n qlysanbong deploy/$name -f --tail=50 2>&1 | ForEach-Object {
        $line = $_

        # Bo qua dong nhieu
        if ($line -match "Hibernate:|HikariPool|o\.s\.|o\.h\.|c\.z\.|tomcat|DispatcherServlet|SpringPersistence|LogHelper|JtaPlatform|WARN.*open-in-view|Starting.*Application|Bootstrapping|Finished Spring") {
            return
        }

        # To mau theo loai log
        if ($line -match "\[THEM KHACH HANG\].*Thanh cong" -or $line -match "\[NHAP HANG\].*Thanh cong") {
            Write-Host $line -ForegroundColor Green
        } elseif ($line -match "\[THEM KHACH HANG\]" -or $line -match "\[NHAP HANG\].*Bat dau") {
            Write-Host $line -ForegroundColor Cyan
        } elseif ($line -match "\[NHAP HANG\].*Chi tiet") {
            Write-Host $line -ForegroundColor Yellow
        } elseif ($line -match "VALIDATE THAT BAI|LOI DU LIEU") {
            Write-Host $line -ForegroundColor Red
        } elseif ($line -match "LOI HE THONG|ERROR") {
            Write-Host $line -ForegroundColor Magenta
        } elseif ($line -match "Started.*in") {
            Write-Host "[$name] SERVICE SAN SANG" -ForegroundColor Green
        } elseif ($line.Trim() -ne "") {
            Write-Host $line -ForegroundColor $color
        }
    }
}

switch ($service) {
    "customer" { Watch-Service "customer-service" "White" }
    "import"   { Watch-Service "import-service"   "White" }
    "supplier" { Watch-Service "supplier-service" "White" }
    "product"  { Watch-Service "product-service"  "White" }
    default {
        Write-Host "=== THEO DOI LOG TAT CA SERVICE ===" -ForegroundColor DarkCyan
        Write-Host "customer | import | supplier | product" -ForegroundColor DarkGray
        Write-Host "Dung: .\logs.ps1 -service customer" -ForegroundColor DarkGray
        Write-Host "--------------------------------------`n"

        $jobs = @(
            Start-Job { kubectl logs -n qlysanbong deploy/customer-service -f --tail=20 2>&1 | ForEach-Object { "[customer] $_" } },
            Start-Job { kubectl logs -n qlysanbong deploy/import-service   -f --tail=20 2>&1 | ForEach-Object { "[import  ] $_" } },
            Start-Job { kubectl logs -n qlysanbong deploy/supplier-service -f --tail=20 2>&1 | ForEach-Object { "[supplier] $_" } },
            Start-Job { kubectl logs -n qlysanbong deploy/product-service  -f --tail=20 2>&1 | ForEach-Object { "[product ] $_" } }
        )

        try {
            while ($true) {
                foreach ($job in $jobs) {
                    $output = Receive-Job $job
                    foreach ($line in $output) {
                        if ($line -match "Hibernate:|HikariPool|o\.s\.|o\.h\.|tomcat|DispatcherServlet") { continue }

                        if     ($line -match "Thanh cong")           { Write-Host $line -ForegroundColor Green }
                        elseif ($line -match "\[customer\]")          { Write-Host $line -ForegroundColor Cyan }
                        elseif ($line -match "\[import")              { Write-Host $line -ForegroundColor Yellow }
                        elseif ($line -match "\[supplier\]")          { Write-Host $line -ForegroundColor Blue }
                        elseif ($line -match "\[product")             { Write-Host $line -ForegroundColor Magenta }
                        elseif ($line -match "ERROR|VALIDATE THAT BAI") { Write-Host $line -ForegroundColor Red }
                        elseif ($line.Trim() -ne "")                  { Write-Host $line }
                    }
                }
                Start-Sleep -Milliseconds 300
            }
        } finally {
            $jobs | Remove-Job -Force
        }
    }
}
