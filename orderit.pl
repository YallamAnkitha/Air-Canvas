use strict;
use warnings;

# Read number of instructions
my $n = <STDIN>;
chomp $n;

# Skip the 'shuffled' label
<STDIN>;

# Read shuffled instructions
my @shuffled;
for (1..$n) {
    my $line = <STDIN>;
    chomp $line;
    push @shuffled, $line;
}

# Skip the 'original' label
<STDIN>;

# Read original instructions
my @original;
for (1..$n) {
    my $line = <STDIN>;
    chomp $line;
    push @original, $line;
}

# Initialize LCS table
my @dp;
for my $i (0..$n) {
    for my $j (0..$n) {
        $dp[$i][$j] = 0;
    }
}

# Fill LCS table
for my $i (1..$n) {
    for my $j (1..$n) {
        if ($shuffled[$i-1] eq $original[$j-1]) {
            $dp[$i][$j] = $dp[$i-1][$j-1] + 1;
        } else {
            $dp[$i][$j] = $dp[$i-1][$j] > $dp[$i][$j-1] ? $dp[$i-1][$j] : $dp[$i][$j-1];
        }
    }
}

# Minimum operations = total - LCS length
my $min_ops = $n - $dp[$n][$n];
print "$min_ops\n";
