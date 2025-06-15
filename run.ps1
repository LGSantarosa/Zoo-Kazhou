# Script para executar o projeto Zoo Kazhou

# --- CONFIGURAÇÃO ---
# Caminho para o diretório 'bin' da sua instalação do JDK.
$JDK_BIN_PATH = "C:\Program Files\Java\jdk-24\bin"
# --------------------

# Define os caminhos para os executáveis do Java
$Java = Join-Path $JDK_BIN_PATH "java.exe"
If (-not (Test-Path $Java)) {
    Write-Host "ERRO: Executável Java (java.exe) não encontrado no caminho: $Java"
    Write-Host "Por favor, verifique o caminho da variável `$JDK_BIN_PATH` no início deste script."
    exit
}

# Define o diretório de saída
$OUTPUT_DIR = ".\out"

# Verifica se o diretório de saída existe
if (-not (Test-Path $OUTPUT_DIR)) {
    Write-Host "ERRO: Diretório de saída não encontrado. Execute compile.ps1 primeiro."
    exit
}

# Executa o programa
java -cp $OUTPUT_DIR zoo.Main 