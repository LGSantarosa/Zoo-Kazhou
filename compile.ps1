# Script para compilar o projeto Zoo Kazhou

# --- CONFIGURAÇÃO ---
# Caminho para o diretório 'bin' da sua instalação do JDK.
$JDK_BIN_PATH = "C:\Program Files\Java\jdk-24\bin"
# --------------------

# Define os caminhos para os executáveis do Java
$Javac = Join-Path $JDK_BIN_PATH "javac.exe"
If (-not (Test-Path $Javac)) {
    Write-Host "ERRO: Compilador Java (javac.exe) não encontrado no caminho: $Javac"
    Write-Host "Por favor, verifique o caminho da variável `$JDK_BIN_PATH` no início deste script."
    exit
}

# Define o classpath, incluindo a biblioteca Gson
$CLASSPATH = ".\lib\gson-2.10.1.jar;."

# Encontra todos os arquivos .java no projeto
$javaFiles = Get-ChildItem -Path ".\src\main\java" -Recurse -Filter *.java | ForEach-Object { $_.FullName }

# Cria o diretório de saída, se não existir
$outputDir = ".\out"
if (-not (Test-Path $outputDir)) {
    New-Item -ItemType Directory -Path $outputDir
}

# Imprime uma mensagem e executa a compilação
Write-Host "Compilando arquivos Java..."
& $Javac -cp $CLASSPATH -d $outputDir $javaFiles
Write-Host "Compilação de código concluída."

# Copia os recursos (arquivos estáticos do frontend) para o diretório de saída
$sourceResources = ".\src\main\resources"
if (Test-Path $sourceResources) {
    Write-Host "Copiando recursos de $sourceResources para $outputDir..."
    Copy-Item -Path "$sourceResources\*" -Destination $outputDir -Recurse -Force
}

Write-Host "Processo de build finalizado!"
Write-Host "Os arquivos compilados e recursos estão no diretório 'out'." 