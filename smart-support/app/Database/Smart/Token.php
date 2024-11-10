<?php
/**
 */
namespace App\Database\Smart;

use Carbon\Carbon;

/**
 */
class Token extends AbstractModel
{
	/**
	 */
	protected $table = 'token';
	
	/**
	 */
	protected $primaryKeys = [
		'user_id' => 'user_id',
		'token_type_id' => 'token_type_id',
		'prefix' => 'prefix',
		'suffix' => 'suffix',
		'unique' => 'unique'
	];

	/**
	 */
	protected $casts = [
		'created' => 'datetime',
		'expired' => 'datetime'
	];

	/**
	 */
	protected $user;
	protected $tokenType;
	protected $tokenStatus;
	
	/**
	 */
	public function isExpired(){
		return Carbon::now() > $this->expired;
	}
	
	/**
	 */
	public function isNotExpired(){
		return !$this->isExpired();
	}

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
	public function tokenType(){
		return TokenType::where([
			'token_type.id' => $this->token_type_id
		]);
	}

	/**
	 */
	public function getTokenType(){
		if ($this->tokenType == null) {
			$this->tokenType = $this->tokenType()->first();
		}
		return $this->tokenType;
	}

	/**
	 */
	public function tokenStatus(){
		return TokenStatus::where([
			'token_status.id' => $this->token_status_id
		]);
	}

	/**
	 */
	public function getTokenStatus(){
		if ($this->tokenStatus == null) {
			$this->tokenStatus = $this->tokenStatus()->first();
		}
		return $this->tokenStatus;
	}
}