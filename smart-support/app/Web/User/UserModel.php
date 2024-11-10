<?php
/**
 */
namespace App\Web\User;

use App\Support\Media;

/**
 */
class UserModel
{
	/**
	 */
	public function isTokenExists(){
		return User::isTokenExists();
	}

	/**
	 */
	public function getToken(){
		return User::getToken();
	}
	
	/**
	 */
	public function getUser(){
		return User::getUser();
	}

	/**
	 */
	public function getUserId(){
		return $this->getUser()->id;
	}

	/**
	 */
	public function getUserName(){
		return $this->getUser()->name;
	}

	/**
	 */
	public function getUserFirstName(){
		return $this->getUser()->first_name;
	}

	/**
	 */
	public function getUserLastName(){
		return $this->getUser()->last_name;
	}

	/**
	 */
	public function getUserType(){
		return $this->getUser()->getUserType();
	}

	/**
	 */
	public function getUserStatus(){
		return $this->getUser()->getUserStatus();
	}

	/**
	 */
	public function getUserPhotoFitUrl($width, $height, $position = 'c'){
		return Media::buildFitUrlFromUser($this->getUser(), $width, $height, $position);
	}

	/**
	 */
	public function getUserPhotoFileUrl(){
		return Media::buildFileUrlFromUser($this->getUser());
	}

	/**
	 */
	public function getUserRoot(){
		return $this->getUser()->getUserRoot();
	}

	/**
	 */
	public function getUserSystem(){
		return $this->getUser()->getUserSystem();
	}

	/**
	 */
	public function getUserSuperadmin(){
		return $this->getUser()->getUserSuperadmin();
	}

	/**
	 */
	public function getUserAdmin(){
		return $this->getUser()->getUserAdmin();
	}

	/**
	 */
	public function getUserCustody(){
		return $this->getUser()->getUserCustody();
	}

	/**
	 */
	public function getUserDriver(){
		return $this->getUser()->getUserDriver();
	}
	
	/**
	 */
	public function getUserLogin(){
		return $this->getUser()->getUserTimezone()->formatDateTimeFromObject($this->getToken()->created);
	}
}