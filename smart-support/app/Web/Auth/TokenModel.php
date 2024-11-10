<?php
/**
 */
namespace App\Web\Auth;

use Carbon\Carbon;
use App\Database\Smart;
use App\Support\Uid;

/**
 */
class TokenModel
{
	/**
	 */
	protected $userId;
	protected $tokenTypeId;
	protected $unique;
	protected $created;
	protected $expired;
	protected $prefix;
	protected $suffix;
	
	/**
	 */
	public function getUserId(){
		return $this->userId;
	}

	/**
	 */
	public function getTokenTypeId(){
		return $this->tokenTypeId;
	}
	
	/**
	 */
	public function getUnique(){
		return $this->unique;
	}

	/**
	 */
	public function getCreated(){
		return $this->created;
	}
	
	/**
	 */
	public function getExpired(){
		return $this->expired;
	}

	/**
	 */
	public function getPrefix(){
		if ($this->prefix === null) {
			$this->prefix = $this->created->format(Uid::FORMAT);
		}
		return $this->prefix;
	}

	/**
	 */
	public function getSuffix(){
		if ($this->suffix === null) {
			$this->suffix = $this->expired->format(Uid::FORMAT);
		}
		return $this->suffix;
	}

	/**
	 */
	public function validate(){
		return ctype_digit($this->userId) &&
			   ctype_digit($this->tokenTypeId) &&
			   ctype_alnum($this->unique) &&
			   $this->tokenTypeId == Smart\TokenType::TYPE_SESSION_ID &&
			   ($now = Carbon::now()) >= $this->created && $now <= $this->expired;
	}

	/**
	 */
	public function toJson(){
		return json_encode($this->toArray());
	}

	/**
	 */
	public function toArray(){
		return[
			(string)$this->userId,
			(string)$this->tokenTypeId,
			(string)$this->unique,
			$this->created->getTimestamp(),
			$this->expired->getTimestamp()
		];
	}
	
	/**
	 */
	public function toToken(){
		return Smart\Token::select()
			->where('token.user_id', $this->userId)
			->where('token.token_type_id', $this->tokenTypeId)
			->where('token.prefix', $this->getPrefix())
			->where('token.suffix', $this->getSuffix())
			->where('token.unique', $this->unique)
			->where('token.token_status_id', Smart\TokenStatus::STATUS_ACTIVE_ID)
			->where('token.expired', '>', Carbon::now())
			->first();
	}

	/**
	 */
	public static function fromJson($token){
		$token = json_decode($token);
		if (is_array($token) && count($token) === 5) {
			return static::fromArray($token);
		}
	}

	/**
	 */
	public static function fromArray(array $token){
		$model = new static();
		$model->userId = $token[0];
		$model->tokenTypeId = $token[1];
		$model->unique = $token[2];
		$model->created = Carbon::createFromTimestamp($token[3]);
		$model->expired = Carbon::createFromTimestamp($token[4]);
		return $model;
	}
	
	/**
	 */
	public static function fromToken(Smart\Token $token){
		$model = new static();
		$model->userId = $token->user_id;
		$model->tokenTypeId = $token->token_type_id;
		$model->unique = $token->unique;
		$model->created = $token->created;
		$model->expired = $token->expired;
		return $model;
	}
}