<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Controllers;

use Illuminate\Http\Request;
use App\Http\Bundle\Service\Api\Models;

/**
 */
class TaxController extends AbstractController
{
    /*-------------------------------------------------------------------------
     * /tax/data
     *-------------------------------------------------------------------------
     */
    public function data(Request $request)
    {
        $data = new Models\Tax\Data($request);
        if (!$data->validate()) {
            return $this->jsonError()
                ->params($data->getValidator()->errors())
                ->build();
        }
        return $this->jsonData($data->response())
            ->build();
    }

    /*-------------------------------------------------------------------------
     * /tax/create
     *-------------------------------------------------------------------------
     */
    public function create(Request $request)
    {
        $create = new Models\Tax\Create($request);
        if (!$create->validate()) {
            return $this->jsonError()
                ->params($create->getValidator()->errors())
                ->build();
        }
        $create->create();
        return $this->jsonSuccess()
            ->build();
    }

    /*-------------------------------------------------------------------------
     * /tax/update
     *-------------------------------------------------------------------------
     */
    public function update(Request $request)
    {
        $update = new Models\Tax\Update($request);
        if (!$update->validate()) {
            return $this->jsonError()
                ->params($update->getValidator()->errors())
                ->build();
        }
        $update->update();
        return $this->jsonSuccess()
            ->build();
    }


    /*-------------------------------------------------------------------------
     * /tax/delete
     *-------------------------------------------------------------------------
     */
    public function delete(Request $request)
    {
        $delete = new Models\Tax\Delete($request);
        if (!$delete->validate()) {
            return $this->jsonError()
                ->params($delete->getValidator()->errors())
                ->build();
        }
        $delete->delete();
        return $this->jsonSuccess()
            ->build();
    }
}