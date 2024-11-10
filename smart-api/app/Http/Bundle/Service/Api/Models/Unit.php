<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models;

use Carbon\Carbon;
use Illuminate\Http\Request;
use Wayang\Stdlib\Nik\Nik;
use App\Database\Smart;
use App\Support;
use App\Service;
use Exception;

/**
 */
class Unit extends Support\Validator\AbstractValidator
{
	/**
	 */
	protected $validatorRules = [
    ];

    protected $units;

	/**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
        $this->units = Smart\Unit::select()
            ->get();
        return true;
    }

    /**
	 */
	public function response(){
        return $this->units;
    }
}