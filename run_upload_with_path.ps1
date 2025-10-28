# Wrapper to set PATH to the discovered Git cmd folder and run upload script
$gitDir = 'C:\Program Files\Git\cmd'
if (-not (Test-Path (Join-Path $gitDir 'git.exe'))) {
    Write-Error "git.exe not found in $gitDir"
    exit 1
}
$env:PATH = "$gitDir;" + $env:PATH
Write-Output "Using git from: $gitDir"
git --version
Set-Location 'C:\Users\ankit\OneDrive\Desktop\aaiirr canvas'
# Run the upload script
.\upload_to_github.ps1
