Param(
    [string]$RepoUrl = "https://github.com/YallamAnkitha/Air-Canvas.git",
    [string]$Branch = "main",
    [string]$CommitMessage = "Initial commit from air_canvas workspace"
)

# Use the script directory as workspace root
$root = $PSScriptRoot
if (-not $root) { $root = Split-Path -Parent $MyInvocation.MyCommand.Definition }
Write-Output "Workspace: $root"

# Check for git
$git = Get-Command git -ErrorAction SilentlyContinue
if (-not $git) {
    Write-Error "Git executable not found in PATH. Please install Git for Windows (https://git-scm.com/download/win) and re-run this script."
    exit 1
}

# Create a sensible .gitignore if one doesn't exist
$gitignorePath = Join-Path $root '.gitignore'
if (-not (Test-Path $gitignorePath)) {
    @"
# Python
__pycache__/
*.py[cod]
*$py.class

# virtual envs
aircanvas-env/
env/
venv/
ENV/
.venv/

# packaging
build/
dist/
*.egg-info/

# IDEs / editors
.vscode/
.idea/
*.suo
*.user
*.userosscache
*.sln.docstates

# OS
.DS_Store
Thumbs.db

# secrets
.env

# pytest
.pytest_cache/

"@ | Out-File -Encoding UTF8 -FilePath $gitignorePath
    Write-Output "Created .gitignore"
} else {
    Write-Output ".gitignore already exists, leaving it unchanged."
}

# Initialize repository if needed
if (-not (Test-Path (Join-Path $root '.git'))) {
    Write-Output "Initializing git repository..."
    try {
        git -C $root init -b $Branch
    } catch {
        git -C $root init
        git -C $root branch -M $Branch
    }
} else {
    Write-Output "Folder is already a git repository."
}

# Ensure local user identity is configured (only sets if empty)
try {
    $name = git -C $root config user.name
} catch { $name = $null }
if ([string]::IsNullOrWhiteSpace($name)) { git -C $root config user.name "Ankitha"; Write-Output "Set git user.name to Ankitha (local)" }
try {
    $email = git -C $root config user.email
} catch { $email = $null }
if ([string]::IsNullOrWhiteSpace($email)) { git -C $root config user.email "ankitha@example.com"; Write-Output "Set git user.email to ankitha@example.com (local)" }

# Stage and commit
git -C $root add .
$commitOutput = git -C $root commit -m "$CommitMessage" 2>&1
if ($LASTEXITCODE -eq 0) {
    Write-Output "Committed changes."
} else {
    Write-Output "Commit step: $commitOutput"
    Write-Output "(This likely means there was nothing new to commit.)"
}

# Add remote and push
# Remove existing origin if present to avoid conflicts
git -C $root remote remove origin 2>$null
git -C $root remote add origin $RepoUrl

Write-Output "Pushing to $RepoUrl on branch $Branch. You may be prompted to authenticate."
$pushOutput = git -C $root push -u origin $Branch 2>&1
if ($LASTEXITCODE -ne 0) {
    Write-Output "Push failed with exit code $LASTEXITCODE. Output:\n$pushOutput"
    Write-Output "If authentication is required, options: install Git Credential Manager (recommended), set up SSH keys and use the SSH repo URL, or use a Personal Access Token (PAT) when prompted."
    exit $LASTEXITCODE
} else {
    Write-Output "Push succeeded."
}

Write-Output "Done. If you want to re-run with a different repo/branch/commit message, call the script with parameters e.g.:"
Write-Output "    .\upload_to_github.ps1 -RepoUrl 'https://github.com/you/yourrepo.git' -Branch 'main' -CommitMessage 'My message'"
