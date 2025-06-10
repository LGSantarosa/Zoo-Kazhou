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

# Define o classpath, incluindo a biblioteca Gson e o diretório de saída 'out'
$CLASSPATH = ".\lib\gson-2.10.1.jar;.\out"

# Imprime uma mensagem e executa o servidor
Write-Host "Iniciando o servidor Zoo Kazhou..."
& $Java -cp $CLASSPATH zoo.web.ZooServer 