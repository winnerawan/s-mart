<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Controllers;

use Illuminate\Http\Request;
use App\Http\Bundle\Service\Api\Models;

/**
 */
class SupplierController extends AbstractController
{
    /*-------------------------------------------------------------------------
     * /supplier/data
     *-------------------------------------------------------------------------
     */
    public function data(Request $request)
    {
        $data = new Models\Supplier\Data($request);
        if (!$data->validate()) {
            return $this->jsonError()
                ->params($data->getValidator()->errors())
                ->build();
        }
        return $this->jsonData($data->response())
            ->build();
    }

    /*-------------------------------------------------------------------------
     * /supplier/create
     *-------------------------------------------------------------------------
     */
    public function create(Request $request)
    {
        $create = new Models\Supplier\Create($request);
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
     * /supplier/update
     *-------------------------------------------------------------------------
     */
    public function update(Request $request)
    {
        $update = new Models\Supplier\Update($request);
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
     * /supplier/updatePosition
     *-------------------------------------------------------------------------
     */
    public function updatePosition(Request $request)
    {
        $updatePosition = new Models\Supplier\Update($request);
        if (!$updatePosition->validate()) {
            return $this->jsonError()
                ->params($updatePosition->getValidator()->errors())
                ->build();
        }
        $updatePosition->updatePosition();
        return $this->jsonSuccess()
            ->build();
    }

    /*-------------------------------------------------------------------------
     * /supplier/delete
     *-------------------------------------------------------------------------
     */
    public function delete(Request $request)
    {
        $delete = new Models\Supplier\Delete($request);
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