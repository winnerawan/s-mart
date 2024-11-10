<?php
/**
 */
namespace App\Database\Smart;

use Illuminate\Support\Facades\Hash;

/**
 */
class Password extends AbstractModel
{
	/**
	 */
	protected $table = 'password';
	
	/**
	 */
	protected $primaryKeys = [
		'user_id' => 'user_id'
	];
	
	/**
	 */
	protected $user;
	
	/**
	 */
	public function user(){
		return User::where([
			'user.id' => $this->user_id
		]);
	}

	/**
	 */
	public function getUser(){
		if ($this->user == null) {
			$this->user = $this->user()->first();
		}
		return $this->user;
	}

	/**
	 */
	public function isPassword($password){
		return Hash::check($password, $this->password);
	}

	/**
	 */
	public function setPassword($password){
		$this->password = Hash::make($password);
	}
}