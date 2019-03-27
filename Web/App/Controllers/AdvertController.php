<?php
namespace App\Controllers;

use App\Models;
use App\Results;
use App\Response;

class AdvertController
{
    private $advertModel;

    public function __construct()
    {
        $this->advertModel = new Models\AdvertModel();
    }
    
    public function create()
    {
        if (!empty($_POST['VehicleReg']) &&
            !empty($_POST['Description']) &&
            !empty($_POST['Price']) &&
            !empty($_POST['SellerID']))
        {
            $vehicleReg = $_POST['VehicleReg'];
            $description = $_POST['Description'];
            $price = $_POST['Price'];
            $sellerId = $_POST['SellerID'];
            
            $this->advertModel->create($vehicleReg, $description, $price, $sellerId);
            return new Results\JsonResult("Advert was created successfully.");
        }

        return new Results\JsonResult("Unable to create the Advert.", Response::BAD_REQUEST);
    }

    public function upload_image()
    {
       // file_put_contents('images/image2.jpg', $_POST['image']);
       //return new Results\JsonResult(var_dump($_POST));

       $target = 'images/' . basename($_FILES['bitmap']['name']);
       move_uploaded_file($_FILES['bitmap']['tmp_name'], $target);

       return new Results\JsonResult("Done");
    }

    public function read()
    {
        // Read by ID
        if (isset($_GET['id'])) 
        {
            $id = $_GET['id'];
            $advert = $this->advertModel->getById($id);
            if (!$advert)
                return new Results\JsonResult("No Advert found for the specified ID.");
            return new Results\JsonResult($advert);
        }

        // Read all
        $adverts = $this->advertModel->getAll();
        return new Results\JsonResult($adverts);
    }

    public function update()
    {
        if (!empty($_POST['ID']) &&
            !empty($_POST['VehicleReg']) &&
            !empty($_POST['Description']) &&
            !empty($_POST['Price']) &&
            !empty($_POST['SellerID']))
        {
            $id = $_POST['ID'];
            $vehicleReg = $_POST['VehicleReg'];
            $description = $_POST['Description'];
            $price = $_POST['Price'];
            $sellerId = $_POST['SellerID'];
            
            $this->advertModel->update($id, $vehicleReg, $description, $price, $sellerId);
            return new Results\JsonResult("Advert was updated successfully.");
        }

        return new Results\JsonResult("Unable to update the Advert.", Response::BAD_REQUEST);
    }
}