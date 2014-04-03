![BTCReceive](walrus-256.png)

A BIP-0032 Hierarchical Deterministic Bitcoin Wallet
====================================================

This wallet uses BIP-0032 to implement a hierarchical deterministic
wallet.

Features include:

* Multiple logical "accounts" within each wallet.
* Fresh receive and change addresses are used for each transfer.
* Wallet only needs to be backed up once, on initial creation.
* Wallet backup consists of simple list of 12 common words (BIP-0039).
* Same wallet may be securely accessed concurrently from multiple devices.
* Wallet data is protected by scrypt passcode.

BTCReceive is available in the Google play store:

[https://play.google.com/store/apps/details?id=com.bonsai.btcreceive](https://play.google.com/store/apps/details?id=com.bonsai.btcreceive)

Beta Test Version
================

A beta test version of BTCReceive is available in the google play store
to members of the bitcoinj google group.  If you are in the bitcoinj
google group you can click on this link to install the beta:

[https://play.google.com/apps/testing/com.bonsai.btcreceive](https://play.google.com/apps/testing/com.bonsai.btcreceive)

Alpha Test Version
================

An alpha test version of BTCReceive is available in the google play store.
You'll need to join the
[Wallet32 Google+ Community](https://plus.google.com/u/0/communities/112340435878616981465) to get access to the
pre-release version.

Instructions for installing the alpha version are in the first post ...

Building BTCReceive
===============

### Setup

Clone the project source from github:

    git clone git@github.com:ksedgwic/BTCReceive.git

Import the project into Eclipse:

File -> Import ... -> General -> Existing Projects into Workspace

Add v7 appcompat to Eclipse:

http://developer.android.com/tools/support-library/setup.html#libs-with-res


About BTCReceive
================

BTCReceive

Version: 0.1.0 (03-Apr-2014)

Source:  https://github.com/ksedgwic/BTCReceive

Author:  Ken Sedgwick <ken@bonsai.com>

BTCReceive is made possible by:
* [bitcoinj](https://code.google.com/p/bitcoinj/)
* [bitcoin-wallet](https://github.com/schildbach/bitcoin-wallet)
* [ZXScanLib](https://github.com/LivotovLabs/zxscanlib)
* [blockchain.info](https://blockchain.info)
* [Bitstamp](https://www.bitstamp.net)

Powered by [CoinDesk](http://www.coindesk.com/price)

Donate: [14qJ7PAfKCBtcMHzQNerHwUtgt4HVSZFRL](bitcoin:14qJ7PAfKCBtcMHzQNerHwUtgt4HVSZFRL)

Copyright (C) 2014 Bonsai Software, Inc.  All rights reserved.
