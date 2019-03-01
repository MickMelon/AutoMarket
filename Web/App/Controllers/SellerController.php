<?php
namespace App\Controllers;

use App\Models;
use App\Results;

class SellerController 
{
    private $sellerModel;

    public function __construct() 
    {
        $this->sellerModel = new Models\SellerModel();
    }

    public function create()
    {       
        if (!empty($_POST['Email']) &&
            !empty($_POST['PhoneNumber']) &&
            !empty($_POST['Name']) &&
            !empty($_POST['Website']) &&
            !empty($_POST['Description']) &&
            !empty($_POST['Location']))
        {
            $email = $_POST['Email'];
            $phoneNumber = $_POST['PhoneNumber'];
            $name = $_POST['Name'];
            $website = $_POST['Website'];
            $description = $_POST['Description'];
            $location = $_POST['Location'];
            
            $this->sellerModel->create($email, $phoneNumber, $name, $website, $description, $location);
            return new Results\JsonResult("Seller was created successfully.");
        }

        return new Results\JsonResult("Unable to create the Seller.");
    }

    public function read()
    {
        if (isset($_GET['id'])) 
        {
            $id = $_GET['id'];
            $seller = $this->sellerModel->get($id);
            if (!$seller)
                return new Results\JsonResult("No Seller found for the specified ID.");
            return new Results\JsonResult($seller);
        }

        // View Error
        return Results\ViewResult::error();
    }

    public function update()
    {

    }

    public function delete()
    {

    }
}