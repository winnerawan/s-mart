<?php
/**
 */
namespace App\Database\Smart;

use DateTime;
use DateTimeZone;
use Carbon\Carbon;

/**
 */
class UserTimezone extends AbstractModel
{
	/**
	 */
	const FORMAT_TIME = 'H:i';
	const FORMAT_DATE = 'd M Y';
	const FORMAT_DATETIME = 'd M Y, H:i';

	/**
	 */
	protected $table = 'user_timezone';
	
	/**
	 */
	protected $primaryKeys = [
		'user_id' => 'user_id'
	];

	/**
	 */
	protected $user;
	protected $timezone;

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
	public function timezone(){
		return Timezone::where([
			'id' => $this->timezone_id
		]);
	}
	
	/**
	 */
	public function getTimezone(){
		if ($this->timezone == null) {
			$this->timezone = $this->timezone()->first();
		}
		return $this->timezone;
	}

	/**
	 */
	public function parseFromString($dateTime){
		if (!$dateTime) return;
		return $this->parseFromObject(Carbon::parse($dateTime));
	}

	/**
	 */
	public function parseFromObject(DateTime $dateTime){
		if (!$dateTime) return;
		$dateTime->setTimezone(new DateTimeZone($this->getTimezone()->value));
		return $dateTime;
	}

	/**
	 */
	public function formatTimeFromString($dateTime){
		if (!$dateTime) return;
		return $this->parseFromString($dateTime)->format(static::FORMAT_TIME);
	}

	/**
	 */
	public function formatTimeFromObject($dateTime){
		if (!$dateTime) return;
		return $this->parseFromObject($dateTime)->format(static::FORMAT_TIME);
	}
	
	/**
	 */
	public function formatDateFromString($dateTime){
		if (!$dateTime) return;
		return $this->parseFromString($dateTime)->format(static::FORMAT_DATE);
	}

	/**
	 */
	public function formatDateFromObject($dateTime){
		if (!$dateTime) return;
		return $this->parseFromObject($dateTime)->format(static::FORMAT_DATE);
	}

	/**
	 */
	public function formatDateTimeFromString($dateTime){
		if (!$dateTime) return;
		return $this->parseFromString($dateTime)->format(static::FORMAT_DATETIME);
	}

	/**
	 */
	public function formatDateTimeFromObject($dateTime){
		if (!$dateTime) return;
		return $this->parseFromObject($dateTime)->format(static::FORMAT_DATETIME);
	}
}