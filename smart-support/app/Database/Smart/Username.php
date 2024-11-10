<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class Username extends AbstractModel
{
	/**
	 */
	protected $table = 'username';
	
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
}