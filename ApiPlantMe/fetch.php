<?php
$url = 'http://www.google.fr/';
echo htmlspecialchars(implode('', file($url)));
?>
