<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Tax;

use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Database\Smart;
use App\Service\Api;
use App\Support;

/**
 */
class Update extends Api\User\UserValidator
{
    /**
	 */
	protected $validatorRules = [
        'tax' => 'required|numeric',
        'name' => 'nullable|string',
        'value' => 'nullable|numeric',
    ];

    protected $tax;

     /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
        $this->tax = Smart\Tax::select()
            ->where('tax.id', $this->validatorData->get('tax'))
            ->first();
        if (!$this->tax) {
            return false;
        }
        return true;
    }

    /**
     */
    public function update() {
        return Smart\User::transaction(function(){
            $this->tax->name = $this->validatorData->get('name');
            $this->tax->value = $this->validatorData->get('value');
            // $this->tax->updated_user_id = $this->getUser()->id;
            // $this->tax->is_updated = 1;
            $this->tax->save();
		});
    }
}