<?php
/**
 */
namespace App\Database\Smart;

use Closure;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Builder;

/**
 */
abstract class AbstractModel extends Model
{
	/**
	 */
	public $timestamps = false;
	public $incrementing = true;

	/**/
	protected $table = '';
	protected $connection = 'smart';
	protected $primaryKeys = ['id' => 'id'];

	/**
	 */
	protected $createdUser;
	protected $updatedUser;

	/**
	 */
	public static function instance(){
		return new static();
	}

	/**
	 */
	public static function connection(){
		return static::instance()->getConnection();
	}

	/**
	 */
	public static function transaction(Closure $callback){
		return static::connection()->transaction($callback);
	}

	/**
	 */
	public static function identifierTable($table){
		$identifier = static::connection()->getDatabaseName();
		if ($table !== null) {
			$identifier .= '.' . $table;
		}
		return $identifier;
	}

	/**
	 */
	public static function identifierColumn($column = null){
		$identifier = static::instance()->getTable();
		if ($column !== null) {
			$identifier .= '.' . $column;
		}
		return static::identifierTable($identifier);
	}

	/**
	 */
	public static function escapeMatchValue($value){
		/* MATCH() ... AGAINST(:value) */
		return preg_replace('/[+\-><\(\)~*\"@\%]+/', '', $value);
	}

	/**
	 */
	public static function escapeMatchSearch($search){
		/* MATCH() ... AGAINST(:search*) */
		$search = static::escapeMatchValue($search);
		if ($search != null) {
			$search .= '*';
		}
		return $search;
	}

	/**
	 */
	public function getKeyNames(){
		return array_keys($this->primaryKeys);
	}

	/**
	 */
	public function getKeyValues(){
		$keys = [];
		foreach ($this->primaryKeys as $keyName => $keyValue){
			$keys[$keyName] = isset($this->original[$keyValue]) ? $this->original[$keyValue] : $this->getAttribute($keyValue);
		}
		return $keys;
	}

	/**
	 */
	protected function setKeysForSaveQuery(Builder $query){
		return $query->where($this->getKeyForSaveQuery());
	}

	/**
	 */
	protected function getKeyForSaveQuery(){
		return $this->getKeyValues();
	}

	/**
	 */
	public function createdUser(){
		return User::where([
			'user.id' => $this->created_user_id
		]);
	}

	/**
	 */
	public function getCreatedUser(){
		if ($this->createdUser == null) {
			$this->createdUser = $this->createdUser()->first();
		}
		return $this->createdUser;
	}
	/**
	 */
	public function updatedUser(){
		return User::where([
			'user.id' => $this->updated_user_id
		]);
	}

	/**
	 */
	public function getUpdatedUser(){
		if ($this->updatedUser == null) {
			$this->updatedUser = $this->updatedUser()->first();
		}
		return $this->updatedUser;
	}
}