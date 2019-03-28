<?php
namespace App\Models;

use App\Database;
use PDO;

class AdvertModel
{
    public function getAll()
    {
        $db = Database::getInstance();

        $sql = "SELECT * FROM `Advert`";
        $query = $db->prepare($sql);
        $query->execute();

        return $query->fetchAll();
    }

    public function getById($id)
    {
        $db = Database::getInstance();

        $sql = "SELECT * FROM `Advert` WHERE `ID` = :id LIMIT 1";
        $query = $db->prepare($sql);
        $query->bindParam(':id', $id, PDO::PARAM_INT);
        $query->execute();

        return $query->fetch();
    }

    public function create($vehicleReg, $description, $price, $sellerId)
    {
        $db = Database::getInstance();

        $sql = "INSERT INTO `Advert` (`VehicleReg`, `Description`, `Price`, `SellerID`)"
            . " VALUES (:vehicleReg, :description, :price, :sellerId)";
        $query = $db->prepare($sql);
        $query->bindParam(':vehicleReg', $vehicleReg, PDO::PARAM_STR);
        $query->bindParam(':description', $description, PDO::PARAM_STR);
        $query->bindParam(':price', $price, PDO::PARAM_STR);
        $query->bindParam(':sellerId', $sellerId, PDO::PARAM_INT);
        $query->execute();

        return $db->lastInsertId();
    }

    public function update($id, $vehicleReg, $description, $price, $sellerId)
    {
        $db = Database::getInstance();

        $sql = "UPDATE `Advert` SET"
            . " `VehicleReg` = :vehicleReg,"
            . " `Description` = :description,"
            . " `Price` = :price,"
            . " `SellerID` = :sellerId"
            . " WHERE `ID` = :id LIMIT 1";
        $query = $db->prepare($sql);
        $query->bindParam(':id', $id, PDO::PARAM_INT);
        $query->bindParam(':vehicleReg', $vehicleReg, PDO::PARAM_STR);
        $query->bindParam(':description', $description, PDO::PARAM_STR);
        $query->bindParam(':price', $price, PDO::PARAM_STR);
        $query->bindParam(':sellerId', $sellerId, PDO::PARAM_INT);
        $query->execute();
    }

    public function updateImage($id, $imageUrl)
    {
        $db = Database::getInstance();

        $sql = "UPDATE `Advert` SET `ImageURL` = :imageUrl WHERE `ID` = :id LIMIT 1";
        $query = $db->prepare($sql);
        $query->bindParam(':imageUrl', $imageUrl, PDO::PARAM_STR);
        $query->bindParam(':id', $id, PDO::PARAM_INT);
        $query->execute();
    }
}