<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class UserBranch extends AbstractModel
{
	/**
	 */
	protected $table = 'user_branch';
	
	/**
	 */
	protected $primaryKeys = [
		'user_id' => 'user_id'
	];

	/**
	 */
	protected $user;
	protected $branch;
	
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
	public function branch(){
		return Branch::where([
			'branch.id' => $this->branch_id
		]);
	}

	/**
	 */
	public function getBranch(){
		if ($this->branch == null) {
			$this->branch = $this->branch()->first();
		}
		return $this->branch;
	}
}