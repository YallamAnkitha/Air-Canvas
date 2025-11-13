use strict;
use warnings;
use Storable qw(dclone);

sub is_solved {
    my ($face) = @_;
    my $color = $face->[0][0];
    for my $row (@$face) {
        for my $cell (@$row) {
            return 0 if $cell ne $color;
        }
    }
    return 1;
}

sub rotate_matrix {
    my ($mat, $dir) = @_;
    my $n = @$mat;
    my @new;
    if ($dir eq 'right') {
        for my $j (0..$n-1) {
            my @col;
            for my $i (reverse 0..$n-1) {
                push @col, $mat->[$i][$j];
            }
            push @new, \@col;
        }
    } elsif ($dir eq 'left') {
        for my $j (reverse 0..$n-1) {
            my @col;
            for my $i (0..$n-1) {
                push @col, $mat->[$i][$j];
            }
            push @new, \@col;
        }
    }
    return \@new;
}

sub apply_instruction {
    my ($cube, $instr, $n) = @_;
    my @parts = split ' ', $instr;
    if ($parts[0] eq 'turn') {
        if ($parts[1] eq 'left') {
            ($cube->{front}, $cube->{left}, $cube->{back}, $cube->{right}) =
            ($cube->{right}, $cube->{front}, $cube->{left}, $cube->{back});
            $cube->{top} = rotate_matrix($cube->{top}, 'right');
            $cube->{base} = rotate_matrix($cube->{base}, 'left');
        } elsif ($parts[1] eq 'right') {
            ($cube->{front}, $cube->{right}, $cube->{back}, $cube->{left}) =
            ($cube->{left}, $cube->{front}, $cube->{right}, $cube->{back});
            $cube->{top} = rotate_matrix($cube->{top}, 'left');
            $cube->{base} = rotate_matrix($cube->{base}, 'right');
        }
    } elsif ($parts[0] eq 'rotate') {
        if ($parts[1] eq 'front') {
            ($cube->{front}, $cube->{base}, $cube->{back}, $cube->{top}) =
            ($cube->{top}, $cube->{front}, $cube->{base}, $cube->{back});
            $cube->{left} = rotate_matrix($cube->{left}, 'right');
            $cube->{right} = rotate_matrix($cube->{right}, 'left');
        } elsif ($parts[1] eq 'back') {
            ($cube->{front}, $cube->{top}, $cube->{back}, $cube->{base}) =
            ($cube->{base}, $cube->{front}, $cube->{top}, $cube->{back});
            $cube->{left} = rotate_matrix($cube->{left}, 'left');
            $cube->{right} = rotate_matrix($cube->{right}, 'right');
        } elsif ($parts[1] eq 'left') {
            ($cube->{top}, $cube->{left}, $cube->{base}, $cube->{right}) =
            ($cube->{right}, $cube->{top}, $cube->{left}, $cube->{base});
            $cube->{front} = rotate_matrix($cube->{front}, 'left');
            $cube->{back} = rotate_matrix($cube->{back}, 'right');
        } elsif ($parts[1] eq 'right') {
            ($cube->{top}, $cube->{right}, $cube->{base}, $cube->{left}) =
            ($cube->{left}, $cube->{top}, $cube->{right}, $cube->{base});
            $cube->{front} = rotate_matrix($cube->{front}, 'right');
            $cube->{back} = rotate_matrix($cube->{back}, 'left');
        }
    } else {
        my ($face, $idx, $dir) = ($parts[0], $parts[1]-1, $parts[2]);
        if ($dir eq 'left') {
            my @row = @{$cube->{$face}[$idx]};
            $cube->{$face}[$idx] = [@row[1..$#row], $row[0]];
        } elsif ($dir eq 'right') {
            my @row = @{$cube->{$face}[$idx]};
            $cube->{$face}[$idx] = [$row[-1], @row[0..$#row-1]];
        } elsif ($dir eq 'up' or $dir eq 'down') {
            my @col = map { $cube->{$face}[$_][$idx] } (0..$n-1);
            if ($dir eq 'up') {
                @col = (@col[1..$#col], $col[0]);
            } else {
                @col = ($col[-1], @col[0..$#col-1]);
            }
            for my $i (0..$n-1) {
                $cube->{$face}[$i][$idx] = $col[$i];
            }
        }
    }
}

sub is_nearly_solved {
    my ($face, $n) = @_;
    my %count;
    for my $row (@$face) {
        for my $cell (@$row) {
            $count{$cell}++;
        }
    }
    for my $c (keys %count) {
        return 1 if $count{$c} == $n*$n - 1;
    }
    return 0;
}

# Main
my ($n, $k) = split ' ', <STDIN>;
my @faces = qw(base back top front left right);
my %cube;
for my $f (@faces) {
    for (1..$n) {
        push @{$cube{$f}}, [split ' ', <STDIN>];
    }
}
my @instr;
for (1..$k) {
    push @instr, <STDIN>;
}
chomp @instr;

for my $i (0..$#instr) {
    my %temp = %{dclone(\%cube)};
    for my $j (0..$#instr) {
        next if $i == $j;
        apply_instruction(\%temp, $instr[$j], $n);
    }
    for my $f (@faces) {
        if (is_solved($temp{$f})) {
            print "$instr[$i]\n";
            exit;
        }
    }
}

for my $i (0..$#instr) {
    my %temp = %{dclone(\%cube)};
    for my $j (0..$#instr) {
        next if $i == $j;
        apply_instruction(\%temp, $instr[$j], $n);
    }
    for my $f (@faces) {
        if (is_nearly_solved($temp{$f}, $n)) {
            print "Faulty\n$instr[$i]\n";
            exit;
        }
    }
}

print "Not Possible\n";
