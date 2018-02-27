
this.getClass().classLoader.rootLoader.addURL(new File("./bitcoincashj-core-0.14.5-bip47-bundled.jar").toURL())


import org.bitcoinj.core.Block
import org.bitcoinj.core.FilteredBlock
import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.core.Peer
import org.bitcoinj.core.listeners.AbstractPeerDataEventListener
import org.bitcoinj.core.listeners.PeerDataEventListener
import org.bitcoinj.params.BCCMainNetParams
import org.bitcoinj.params.BCCTestNet3Params
import org.bitcoinj.params.TestNet3Params
import org.bitcoinj.wallet.bip47.Blockchain
import org.bitcoinj.wallet.bip47.Wallet;
import org.bitcoinj.params.MainNetParams
import org.bitcoinj.wallet.bip47.listeners.BlockchainDownloadProgressTracker
import org.bitcoinj.wallet.bip47.models.StashDeterministicSeed

import javax.annotation.Nullable;
pct = 0

println(args[0]);
blockchains = [new Blockchain(2,BCCMainNetParams.get(), "BCH", "Bitcoin Cash"),
new Blockchain(3, BCCTestNet3Params.get(), "tBCH", "Test Bitcoin Cash"),
new Blockchain(0, MainNetParams.get(), "BTC", "Bitcoin Core"),
new Blockchain(1, TestNet3Params.get(), "tBTC", "Test Bitcoin Core")]


Blockchain blockchain = blockchains.find { it.coin == args[0]}
dir = new File(".");
coin = blockchain.coin
print("Creating new ${coin} wallet ...")
StashDeterministicSeed seed = null
org.bitcoinj.core.Context.propagate(new org.bitcoinj.core.Context(blockchain.getNetworkParameters()));
Wallet BTCwallet = new Wallet(blockchain,dir,seed)
println("done.")

class Listener extends BlockchainDownloadProgressTracker{

    Listener(String coin) {
        super(coin)
    }

    @Override
    int getProgress() {
        return 42;
    }
}
BTCwallet.setBlockchainDownloadProgressTracker(new Listener())

BTCwallet.stop();
print("Starting blockchain download ...")
prevHeight = BTCwallet.vWallet.getLastBlockSeenHeight();
BTCwallet.start(true)
println("done.")

while (true) {
    lastHeight = BTCwallet.vWallet.getLastBlockSeenHeight();
    print("\r")
    print("Last height: ${lastHeight}\r")
    Thread.sleep(1000)
}
println("done")
