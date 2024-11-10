<?php
/**
 */
namespace App\Support;

/**
 */
abstract class Format
{
	/**
	 */
	const SM = 1000;
	const MM = 60000;    // SM * 60
	const HM = 3600000;  // MM * 60
	const DM = 86400000; // HM * 24

	/**
	 */
	public static function formatTime($time){
		$time = explode(':', $time, 3);
		return implode(':', array_splice($time, 0, 2));
	}

	/**
	 */
	public static function formatTimeWib($time){
		$time = explode(':', $time, 3);
		$result = implode(':', array_splice($time, 0, 2));
		return \Carbon\Carbon::parse($result)->setTimezone('Asia/Jakarta')->format('H:i');
	}
	
	/**
	 */
	public static function formatAgeFromDOB($dob) {
		$today = new \DateTime();
		$birthdate = new \DateTime($dob);
		$interval = $today->diff($birthdate);
		// $interval->format('%y years');

		return $interval->format('%y TAHUN');
	}
	/**
	 */
	public static function formatMoney($value){
		return number_format($value, 2, ',', '.');
	}
	/**
	 */
	public static function formatMoneyIDR($value){
		return number_format($value, 0, ',', '.');
	}

	/**
	 */
	public static function toNum($n) {
		$r = '';
		for ($i = 1; $n >= 0 && $i < 10; $i++) {
		$r = chr(0x41 + ($n % pow(26, $i) / pow(26, $i - 1))) . $r;
		$n -= pow(26, $i);
		}
		return $r;
	}
	
	/**
     */
    public static function formatMoneyParentheses($foo) {
        return ($foo < 0 ? "(".number_format(abs($foo), 2, ',', '.').")" : $foo);
    }

	/**
     */
    public static function formatDate($value) {
        if (!$value) {
			return '';
		}
		return \Carbon\Carbon::parse($value)->format('d M Y');
    }

	/**
     */
    public static function formatDateWib($value) {
        if (!$value) {
			return '';
		}
		return \Carbon\Carbon::parse($value)->setTimezone('Asia/Jakarta')->format('d M Y');
    }

	/**
     */
    public static function formatDateTime($value) {
		if (!$value) {
			return '';
		}
        return \Carbon\Carbon::parse($value)->setTimezone('Asia/Jakarta')->format('d M Y, H:i');
    }

	/**
     */
    public static function formatDateTimeWib($value) {
		if (!$value) {
			return '';
		}
        return \Carbon\Carbon::parse($value)->setTimezone('Asia/Jakarta')->format('d M Y, H:i');
    }

	/**
     */
    public static function formatDateTimeToDatePickerFormat($value) {
		if (!$value) {
			return '';
		}
        return \Carbon\Carbon::parse($value)->format('Y-m-d');
    }
	
	/**
	 */
	public static function formatDowntimeFromSecond($second){
		return static::formatDowntimeFromMilisecond($second * static::SM);
	}

	/**
	 */
	public static function formatDowntimeFromMilisecond($milisecond){
		$day = $milisecond / static::DM;
		$milisecond = $milisecond % static::DM;
		$hour = $milisecond / static::HM;
		$milisecond = $milisecond % static::HM;
		$minute = $milisecond / static::MM;
		$milisecond = $milisecond % static::MM;
		$second = $milisecond / static::SM;
		return sprintf('%d/%02d:%02d:%02d', $day, $hour, $minute, $second);
	}

	/**
	 */
	public static function formatTimeworkFromSecond($second){
		return static::formatTimeworkFromMilisecond($second * static::SM);
	}

	/**
	 */
	public static function formatTimeworkFromSecondToDiv($second){
		return static::formatTimeworkFromMilisecondToDiv($second * static::SM);
	}

	/**
	 */
	public static function formatTimeworkFromMilisecond($milisecond){
		$day = $milisecond / static::DM;
		$milisecond = $milisecond % static::DM;
		$hour = $milisecond / static::HM;
		$milisecond = $milisecond % static::HM;
		$minute = $milisecond / static::MM;
		$milisecond = $milisecond % static::MM;
		$second = $milisecond / static::SM;
		$result = [];
		/**/
		$day = (int)$day;
		if ($day > 0) {
			$result[] = $day . ' Hari';
		}
		$hour = (int)$hour;
		if ($hour > 0) {
			$result[] =  $hour . ' Jam';
		}
		$minute = (int)$minute;
		if ($minute > 0) {
			$result[] = $minute . ' Menit';
		}
		$second = (int)$second;
		if ($second > 0) {
			$result[] = $second . ' Detik';
		}
		$result = implode(' ', $result);
		return $result;
	}

	/**
	 */
	public static function formatTimeworkFromMilisecondToDiv($milisecond){
		$day = $milisecond / static::DM;
		$milisecond = $milisecond % static::DM;
		$hour = $milisecond / static::HM;
		$milisecond = $milisecond % static::HM;
		$minute = $milisecond / static::MM;
		$milisecond = $milisecond % static::MM;
		$second = $milisecond / static::SM;
		$result = [];
		/**/
		$day = (int)$day;
		if ($day > 0) {
			$result[] = '<div>'.$day.'</div>';
		} else if ($day <=0 ) {
			$result[] = '<div>0</div>';
		}
		$hour = (int)$hour;
		if ($hour > 0) {
			$result[] =  '<div>'.$hour.'</div>';
		} else if ($hour <=0 ) {
			$result[] = '<div>0</div>';
		}
		$minute = (int)$minute;
		if ($minute > 0) {
			$result[] = '<div>'.$minute.'</div>';
		} else if ($minute <=0 ) {
			$result[] = '<div>0</div>';
		}
		$second = (int)$second;
		if ($second > 0) {
			$result[] = '<div>'.$second.'</div>';
		} else if ($second <=0 ) {
			$result[] = '<div>0</div>';
		}
		$result = implode('', $result);
		return $result;
	}
}