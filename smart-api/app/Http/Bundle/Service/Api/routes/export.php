<?php
/*-------------------------------------------------------------------------
 * /export
 *-------------------------------------------------------------------------
 */

$router->post('/exportTemplatePurchase', [
	'as' => '/exportTemplatePurchase',
	'uses' => 'ExportController@exportTemplatePurchase'
]);

// $router->get('/exportTemplatePurchase', [
// 	'as' => '/exportTemplatePurchase',
// 	'uses' => 'ExportController@exportTemplatePurchase'
// ]);
