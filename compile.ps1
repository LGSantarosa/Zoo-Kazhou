# Script para compilar o projeto Zoo Kazhou

# Define o diretório de saída
$OUTPUT_DIR = ".\out"

# Cria o diretório de saída se não existir
if (-not (Test-Path $OUTPUT_DIR)) {
    New-Item -ItemType Directory -Path $OUTPUT_DIR
}

# Compila o código Java
javac -d $OUTPUT_DIR -cp "src/main/java" src/main/java/zoo/**/*.java

Write-Host "Compilação concluída!"

# Copia os recursos (arquivos estáticos do frontend) para o diretório de saída
$sourceResources = ".\src\main\resources"
if (Test-Path $sourceResources) {
    Write-Host "Copiando recursos de $sourceResources para $OUTPUT_DIR..."
    Copy-Item -Path "$sourceResources\*" -Destination $OUTPUT_DIR -Recurse -Force
}

Write-Host "Processo de build finalizado!"
Write-Host "Os arquivos compilados e recursos estão no diretório '$OUTPUT_DIR'." 