<?php
$n = intval(trim(fgets(STDIN)));
fgets(STDIN);
$shuffled = [];
for ($i = 0; $i < $n; $i++) $shuffled[] = trim(fgets(STDIN));
fgets(STDIN);
$original = [];
for ($i = 0; $i < $n; $i++) $original[] = trim(fgets(STDIN));
$dp = array_fill(0, $n + 1, array_fill(0, $n + 1, 0));
for ($i = 1; $i <= $n; $i++) {
  for ($j = 1; $j <= $n; $j++) {
    if ($shuffled[$i - 1] === $original[$j - 1])
      $dp[$i][$j] = $dp[$i - 1][$j - 1] + 1;
    else
      $dp[$i][$j] = max($dp[$i - 1][$j], $dp[$i][$j - 1]);
  }
}
echo $n - $dp[$n][$n] . "\n";
?>
