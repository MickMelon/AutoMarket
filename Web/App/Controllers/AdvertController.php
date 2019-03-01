<?php
namespace App\Controllers;

use App\Models;
use App\Results;

class AdvertController
{
    private $advertModel;

    public function __construct()
    {
        $this->advertModel = new Models\AdvertModel();
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
            $advert = $this->advertModel->get($id);
            if (!$advert)
                return new Results\JsonResult("No Advert found for the specified ID.");
            return new Results\JsonResult($advert);
        }

        // View Error
        return Results\ViewResult::error();
    }

    public function readAll()
    {
        $adverts = $this->advertModel->getAll();
        if (!$adverts)
            return new Results\JsonResult("No Adverts could be retrieved.");
        return new Results\JsonResult($adverts);
    }
}