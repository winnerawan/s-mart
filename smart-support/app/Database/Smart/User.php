<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class User extends AbstractModel
{
	/**
	 */
	protected $table = 'user';
	
	/**
	 */
	protected $userType;
	protected $userRole;
	protected $userStatus;
	protected $userPhoto;
	protected $userPhone;
	protected $userBranch;
	protected $userTimezone;
	// protected $userFamily;
	protected $username;
	protected $password;
	protected $tokens;
	protected $medias;



	/**
	 */
	public function userType(){
		return UserType::where([
			'user_type.id' => $this->user_type_id
		]);
	}

	/**
	 */
	public function getUserType(){
		if ($this->userType == null) {
			$this->userType = $this->userType()->first();
		}
		return $this->userType;
	}

	/**
	 */
	public function userStatus(){
		return UserStatus::where([
			'user_status.id' => $this->user_status_id
		]);
	}

	/**
	 */
	public function getUserStatus(){
		if ($this->userStatus == null) {
			$this->userStatus = $this->userStatus()->first();
		}
		return $this->userStatus;
	}
	
	/**
	 */
	public function userPhoto(){
		return UserPhoto::where([
			'user_photo.user_id' => $this->id
		]);
	}
	
	/**
	 */
	public function getUserPhoto(){
		if ($this->userPhoto == null) {
			$this->userPhoto = $this->userPhoto()->first();
		}
		return $this->userPhoto;
	}

	/**
	 */
	public function userPhone(){
		return UserPhone::where([
			'user_phone.user_id' => $this->id
		]);
	}

	/**
	 */
	public function getUserPhone(){
		if ($this->userPhone == null) {
			$this->userPhone = $this->userPhone()->first();
		}
		return $this->userPhone;
	}

	/**
	 */
	public function userBranch(){
		return UserBranch::where([
			'user_branch.user_id' => $this->id
		]);
	}
	
	/**
	 */
	public function getUserBranch(){
		if ($this->userBranch == null) {
			$this->userBranch = $this->userBranch()->first();
		}
		return $this->userBranch;
	}

	/**
	 */
	public function userTimezone(){
		return UserTimezone::where([
			'user_timezone.user_id' => $this->id
		]);
	}

	/**
	 */
	public function getUserTimezone(){
		if ($this->userTimezone == null) {
			$this->userTimezone = $this->userTimezone()->first();
		}
		return $this->userTimezone;
	}

	/**
	 */
	public function username(){
		return Username::where([
			'username.user_id' => $this->id
		]);
	}

	/**
	 */
	public function getUsername(){
		if ($this->username == null) {
			$this->username = $this->username()->first();
		}
		return $this->username;
	}

	/**
	 */
	public function password(){
		return Password::where([
			'password.user_id' => $this->id
		]);
	}

	/**
	 */
	public function getPassword(){
		if ($this->password == null) {
			$this->password = $this->password()->first();
		}
		return $this->password;
	}

	/**
	 */
	public function tokens(){
		return Token::where([
			'token.user_id' => $this->id
		]);
	}

	/**
	 */
	public function getTokens(){
		if ($this->tokens == null) {
			$this->tokens = $this->tokens()->get();
		}
		return $this->tokens;
	}

	/**
	 */
	public function medias(){
		return Media::where([
			'media.user_id' => $this->id
		]);
	}

	/**
	 */
	public function getMedias(){
		if ($this->medias == null) {
			$this->medias = $this->medias()->get();
		}
		return $this->medias;
	}

	
}