use strict;
use warnings;

my $n = <STDIN>; chomp $n;
<STDIN>; # skip "shuffled"
my @shuffled;
for (1..$n) {
    my $line = <STDIN>; chomp $line;
    push @shuffled, $line;
}
<STDIN>; # skip "original"
my @original;
for (1..$n) {
    my $line = <STDIN>; chomp $line;
    push @original, $line;
}

my @dp;
for my $i (0..$n) {
    for my $j (0..$n) {
        $dp[$i][$j] = 0;
    }
}

for my $i (1..$n) {
    for my $j (1..$n) {
        if ($shuffled[$i-1] eq $original[$j-1]) {
            $dp[$i][$j] = $dp[$i-1][$j-1] + 1;
        } else {
            $dp[$i][$j] = $dp[$i-1][$j] > $dp[$i][$j-1] ? $dp[$i-1][$j] : $dp[$i][$j-1];
        }
    }
}

print $n - $dp[$n][$n], "\n";
