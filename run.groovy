
this.getClass().classLoader.rootLoader.addURL(new File("./bitcoincashj-core-0.14.5-bip47-bundled.jar").toURL())


import org.bitcoinj.core.*
import org.bitcoinj.params.*;
import org.bitcoinj.kits.*
import org.bitcoinj.wallet.bip47.listeners.BlockchainDownloadProgressTracker

import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.crypto.*;

import javax.annotation.Nullable;
pct = 0

println("BLOCKCHAIN: " + args[0]);

def seed;
if (args.length > 1) {
    String mnemonic = args[1..args.length-1].join(" ");
    println("SEED: ${mnemonic}")

    bip39bornDate = MnemonicCode.BIP39_STANDARDISATION_TIME_SECS;
    seed = new DeterministicSeed(mnemonic, null, "", bip39bornDate);
}

def blockchains = [
    	       // "BCH": BCCMainNetParams.get(),
               // "tBCH": BCCTestNet3Params.get(),
               "BTC": MainNetParams.get(),
               "tBTC": TestNet3Params.get()]

def params = blockchains.get(args[0])

dir = new File(".");
def coin = args[0]

print("Creating new ${coin} wallet ...")

Context.propagate(new Context(params));
bip47App = new BIP47AppKit(coin, params, dir, seed);
println("done.")

println("Payment code: " + bip47App.getPaymentCode());

class Listener extends BlockchainDownloadProgressTracker{

    Listener(String coin) {
        super(coin)
    }

    @Override
    int getProgress() {
        return 42;
    }
}
bip47App.setBlockchainDownloadProgressTracker(new Listener())

bip47App.stop();
print("Starting blockchain download ...")
prevHeight = bip47App.vWallet.getLastBlockSeenHeight();
bip47App.startBlockchainDownload()
println("done.")

while (true) {
    lastHeight = bip47App.getvWallet().getLastBlockSeenHeight();
    print("\r")
    print("Last height: ${lastHeight} Balance: ${bip47App.getvWallet().getBalance()} Peers: ${bip47App.getConnectedPeers().size}\r")
    Thread.sleep(200)
}
println("done")
