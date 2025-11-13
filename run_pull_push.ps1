# Wrapper to set PATH and pull remote changes then push local commits
$gitDir = 'C:\Program Files\Git\cmd'
if (-not (Test-Path (Join-Path $gitDir 'git.exe'))) {
    Write-Error "git.exe not found in $gitDir"
    exit 1
}
$env:PATH = "$gitDir;" + $env:PATH
Write-Output "Using git from: $gitDir"
Set-Location 'C:\Users\ankit\OneDrive\Desktop\aaiirr canvas'
Write-Output "Pulling remote changes (rebase)..."
git pull --rebase origin main
if ($LASTEXITCODE -ne 0) { Write-Output "git pull failed with exit code $LASTEXITCODE"; exit $LASTEXITCODE }
Write-Output "Pushing local commits to origin/main..."
git push -u origin main
exit $LASTEXITCODE
