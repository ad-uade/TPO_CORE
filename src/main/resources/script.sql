INSERT INTO comparativaPrecios VALUES (1, NOW());
INSERT INTO itemsComparativaPrecios (mejorPrecio, numListaPrecios, codigoSFK, codigoPieza, idComparativa, CUILProveedor) VALUES (200,1,'AR','Scotseal',1,56345634332);
INSERT INTO itemsComparativaPrecios (mejorPrecio, numListaPrecios, codigoSFK, codigoPieza, idComparativa, CUILProveedor) VALUES (543.6,2,'AR','18-002-008',1,23735348233)
INSERT INTO itemsComparativaPrecios (mejorPrecio, numListaPrecios, codigoSFK, codigoPieza, idComparativa, CUILProveedor) VALUES (687,3,'AR','18-002-009',1,23709281179);	
INSERT INTO itemsComparativaPrecios (mejorPrecio, numListaPrecios, codigoSFK, codigoPieza, idComparativa, CUILProveedor) VALUES (1120.5,4,'US','YMD',1,23752921791);
INSERT INTO itemsComparativaPrecios (mejorPrecio, numListaPrecios, codigoSFK, codigoPieza, idComparativa, CUILProveedor) VALUES (543,6,'US','JLM',1,23752921777);
INSERT INTO itemsComparativaPrecios (mejorPrecio, numListaPrecios, codigoSFK, codigoPieza, idComparativa, CUILProveedor) VALUES (654,7,'US','EJ',1,34262534233);
INSERT INTO cotizaciones (nroCotizacion, fecha, diasValidez, nroSolicitudCotizacion, CUILCliente, idOficina) VALUES (1,'2015-12-14 01:31:08',7,1,23309281179,3);
INSERT INTO itemsCotizacion (cantidad, precioUnitario, estadoCotizacion, codigoSFK, codigoPieza, nroCotizacion, CUILProveedor, idEstrategiaCliente) VALUES (9,543.6,'PENDIENTE','AR','Scotseal',1,23735348233,NULL);
INSERT INTO itemsCotizacion (cantidad, precioUnitario, estadoCotizacion, codigoSFK, codigoPieza, nroCotizacion, CUILProveedor, idEstrategiaCliente) VALUES (9,543.6,'PENDIENTE','AR','18-002-008',1,23735348233,NULL);
INSERT INTO itemsCotizacion (cantidad, precioUnitario, estadoCotizacion, codigoSFK, codigoPieza, nroCotizacion, CUILProveedor, idEstrategiaCliente) VALUES (9,543.6,'PENDIENTE','AR','18-002-009',1,23735348233,NULL);
INSERT INTO solicitudCotizacion (nroSolicitudCotizacion, fecha, idOficina, CUILCliente) VALUES (1,'2015-12-14 02:04:08',1,23309281179);
INSERT INTO itemsSolicitudCotizacion (cantidad, codigoSFK, codigoPieza, nroSolicitudCotizacion) VALUES (2,'AR','Scotseal',1);
INSERT INTO itemsSolicitudCotizacion (cantidad, codigoSFK, codigoPieza, nroSolicitudCotizacion) VALUES (2,'AR','18-002-008',1);
INSERT INTO itemsSolicitudCotizacion (cantidad, codigoSFK, codigoPieza, nroSolicitudCotizacion) VALUES (2,'AR','18-002-009',1);	
INSERT INTO itemsSolicitudCotizacion (cantidad, codigoSFK, codigoPieza, nroSolicitudCotizacion) VALUES (2,'US','YMD',1);
INSERT INTO itemsSolicitudCotizacion (cantidad, codigoSFK, codigoPieza, nroSolicitudCotizacion) VALUES (2,'US','JLM',1);