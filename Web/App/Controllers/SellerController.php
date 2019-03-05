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
        // Read by ID
        if (isset($_GET['id'])) 
        {
            $id = $_GET['id'];
            $seller = $this->sellerModel->getById($id);
            if (!$seller)
                return new Results\JsonResult("No Seller found for the specified ID.");
            return new Results\JsonResult($seller);
        }

        // Read by Email
        if (isset($_GET['email']))
        {
            $email = $_GET['email'];
            $seller = $this->sellerModel->getByEmail($email);
            if (!$seller)
                return new Results\JsonResult("No Seller found for the specified Email.");
            return new Results\JsonResult($seller);
        }

        // Read all
        $sellers = $this->sellerModel->getAll();
        return new Results\JsonResult($sellers);
    }

    public function update()
    {
        if (!empty($_POST['ID']) &&
            !empty($_POST['Email']) &&
            !empty($_POST['PhoneNumber']) &&
            !empty($_POST['Name']) &&
            !empty($_POST['Website']) &&
            !empty($_POST['Description']) &&
            !empty($_POST['Location']))
        {
            $id = $_POST['ID'];
            $email = $_POST['Email'];
            $phoneNumber = $_POST['PhoneNumber'];
            $name = $_POST['Name'];
            $website = $_POST['Website'];
            $description = $_POST['Description'];
            $location = $_POST['Location'];

            $this->sellerModel->update($id, $email, $phoneNumber, $name, $website, $description, $location);
            return new Results\JsonResult("Seller updated successfully.");
        }

        return new Results\JsonResult("Unable to update the Seller.");
    }

    public function delete()
    {

    }
}