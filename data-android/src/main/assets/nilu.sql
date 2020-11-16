BEGIN TRANSACTION;
INSERT INTO `Network` (id,name,address,active,symbol,chainId,explorerAddress) VALUES (1,'Nilu','https://walletapi.nilu.tech',1,'Ɲ',512,'https://walletapi.nilu.tech/');
INSERT INTO `Network` (id,name,address,active,symbol,chainId,explorerAddress) VALUES (2,'Ethereum','https://mainnet.infura.io',0,'Ξ',1,'https://api.ethplorer.io/');
INSERT INTO `Network` (id,name,address,active,symbol,chainId,explorerAddress) VALUES (3,'Ropsten (Test)','https://ropsten.infura.io',0,'Ξ',3,'');
INSERT INTO `Network` (id,name,address,active,symbol,chainId,explorerAddress) VALUES (4,'Pirl','https://rpc.pirl.minerpool.net',0,'PIRL',3125659152,'http://devpool.nilu.tech/pirl/');
INSERT INTO `Network` (id,name,address,active,symbol,chainId,explorerAddress) VALUES (5,'Ether-1','https://rpc.ether1.org',0,'ETHO',1313114,'https://walletapi.ether1.org/');
COMMIT;
