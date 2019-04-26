<?php
namespace App\Results;

use App\Results\IActionResult;

class JsonResult implements IActionResult
{
    private $data;
    private $response;

    public function __construct($data, $response = 200) 
    {
        $this->data = json_encode($data);
        //var_dump($data);
        $this->response = $response;
    }

    public function execute()
    {
        http_response_code($this->response);
        header('Content-Type: application/json');
        echo $this->data;
    }
}