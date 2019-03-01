<?php
namespace App\Models;

use App\Database;
use PDO;

class SellerModel
{
    public function get($id)
    {
        $db = Database::getInstance();

        $sql = "SELECT * FROM `Seller` WHERE `ID` = :id LIMIT 1";
        $query = $db->prepare($sql);
        $query->bindParam(':id', $id, PDO::PARAM_INT);
        $query->execute();

        return $query->fetch();
    }

    public function create($email, $phoneNumber, $name, $website, $description, $location)
    {
        $db = Database::getInstance();

        $sql = "INSERT INTO `Seller` (`Email`, `PhoneNumber`, `Name`, `Website`, `Description`, `Location`)"
            . " VALUES (:email, :phoneNumber, :name, :website, :description, :location)";
        $query = $db->prepare($sql);
        $query->bindParam(':email', $email, PDO::PARAM_STR);
        $query->bindParam(':phoneNumber', $phoneNumber, PDO::PARAM_STR);
        $query->bindParam(':name', $name, PDO::PARAM_STR);
        $query->bindParam(':website', $website, PDO::PARAM_STR);
        $query->bindParam(':description', $description, PDO::PARAM_STR);
        $query->bindParam(':location', $location, PDO::PARAM_STR);
        $query->execute();
    }

    public function update($id, $email, $phoneNumber, $name, $website, $description, $location)
    {
        $db = Database::getInstance();

        $sql = "UPDATE `Seller` SET"
            . " `Email` = :email,"
            . " `PhoneNumber` = :phoneNumber,"
            . " `Name` = :name,"
            . " `Website` = :website,"
            . " `Description` = :description,"
            . " `Location` = :location"
            . " WHERE `ID` = :id LIMIT 1";
        $query = $db->prepare($sql);
        $query->bindParam(':id', $id, PDO::PARAM_INT);
        $query->bindParam(':email', $email, PDO::PARAM_STR);
        $query->bindParam(':phoneNumber', $phoneNumber, PDO::PARAM_STR);
        $query->bindParam(':name', $name, PDO::PARAM_STR);
        $query->bindParam(':website', $website, PDO::PARAM_STR);
        $query->bindParam(':description', $description, PDO::PARAM_STR);
        $query->bindParam(':location', $location, PDO::PARAM_STR);
        $query->execute();
    }
}