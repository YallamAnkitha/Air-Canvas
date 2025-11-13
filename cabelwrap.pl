use strict;
use warnings;
use List::Util qw(any);

my ($N, $M) = split ' ', <STDIN>;
chomp($N, $M);

my @grid;
for my $i (0 .. $N-1) {
    my $line = <STDIN>;
    chomp $line;
    # Accept space-separated or continuous characters
    my @chars = split /\s+/, $line;
    if (scalar @chars != $M) {
        @chars = split //, $line;
    }
    $grid[$i] = \@chars;
}

# Directions for neighbors: up, down, left, right
my @dr = (-1, 1, 0, 0);
my @dc = (0, 0, -1, 1);

# Check if cell is on edge of grid
sub is_edge {
    my ($r, $c) = @_;
    return $r == 0 || $r == $N-1 || $c == 0 || $c == $M-1;
}

# BFS to find cable path starting from an edge cable cell
my @visited;
for my $i (0 .. $N-1) {
    $visited[$i] = [(0) x $M];
}

my @queue;
my @path;
my $found_start = 0;

for my $r (0 .. $N-1) {
    last if $found_start;
    for my $c (0 .. $M-1) {
        if (is_edge($r,$c) && $grid[$r][$c] eq 'C') {
            push @queue, [$r, $c];
            $visited[$r][$c] = 1;
            $found_start = 1;
            last;
        }
    }
}

while (@queue) {
    my ($r, $c) = @{shift @queue};
    push @path, [$r, $c];
    for my $d (0..3) {
        my $nr = $r + $dr[$d];
        my $nc = $c + $dc[$d];
        if ($nr >= 0 && $nr < $N && $nc >= 0 && $nc < $M) {
            if (!$visited[$nr][$nc] && $grid[$nr][$nc] eq 'C') {
                push @queue, [$nr, $nc];
                $visited[$nr][$nc] = 1;
            }
        }
    }
}

# Count switches needed where cable neighbors rod
my $switches = 0;
OUTER: for my $cell (@path) {
    my ($r, $c) = @$cell;
    for my $d (0..3) {
        my $nr = $r + $dr[$d];
        my $nc = $c + $dc[$d];
        if ($nr >= 0 && $nr < $N && $nc >= 0 && $nc < $M) {
            if ($grid[$nr][$nc] eq 'R') {
                $switches++;
                next OUTER;
            }
        }
    }
}

print "$switches\n";
