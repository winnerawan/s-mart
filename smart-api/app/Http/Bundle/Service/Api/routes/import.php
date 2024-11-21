<?php
/*-------------------------------------------------------------------------
 * /export
 *-------------------------------------------------------------------------
 */

$router->post('/importPurchase', [
	'as' => '/importPurchase',
	'uses' => 'ImportController@importPurchase'
]);

$router->get('/data', [
	'as' => '/data',
	'uses' => 'ImportController@data'
]);

