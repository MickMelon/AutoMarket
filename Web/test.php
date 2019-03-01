<?php
error_reporting(E_ALL);
ini_set("display_errors", 1);

$url = 'http://localhost/~michael/cartrader/carshare/Web/index.php?c=seller&a=create';
$data = array(
    'Name' => 'Michael',
    'Email' => 'email@email.com',
    'PhoneNumber' => '07123456789',
    'Website' => 'www.website.com',
    'Description' => 'Just a short description.',
    'Location' => '12 Hello Lane');

$options = array(
    'http' => array(
        'header' => "Content-Type: application/x-www-form-urlencoded\r\n",
        'method' => 'POST',
        'content' => http_build_query($data)
    )
);

$context = stream_context_create($options);
$result = file_get_contents($url, false, $context);

header('Content-Type: application/json');
echo $result;