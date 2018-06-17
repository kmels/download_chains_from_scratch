// javac BobbyDoucheCash.java -cp bitcoinj-bundle-cash.jar
// javac BobbyDouche.java -cp *.jar; java -cp bitcoinj-bundle.jar:. BobbyDouche
// rm -rf BobAppCash/ && java  -Dbitcoinj.logging=true -cp bitcoinj-bundle-cash.jar:.:slf4j-api-1.7.25.jar:slf4j-simple-1.7.25.jar BobbyDoucheCash
import org.bitcoinj.core.*;
import org.bitcoinj.params.*;
import java.io.File;
//import org.bitcoinj.kits.BIP47AppKit;
//import org.bitcoinj.wallet.bip47.listeners.BlockchainDownloadProgressTracker;
import java.util.*;
import org.bitcoinj.kits.WalletAppKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BobbyBoucheCash {
    private static Logger logger = LoggerFactory.getLogger(BobbyBoucheCash.class);

    public static void main(String as[]) throws Exception {
        String coin = "BTC";
        NetworkParameters params = MainNetParams.get();
        File dir = new File("BobAppCash");

        Context.propagate(new Context(params));
        System.out.println("Creating new " + coin + " wallet ...");
        WalletAppKit appKit = new WalletAppKit(params, dir, "Bobby-BTC");

        // the download will not start unless there is an event listener
        /*class Tracker extends BlockchainDownloadProgressTracker{
            public Tracker(String coin){
                super(coin);
            }
            @Override
            public int getProgress() {
                return 42;
            }
        }
        System.out.println("Adding progress tracker ...");
        appKit.setDownloadListener(new Tracker(coin)).setBlockingStartup(false);

        System.out.println("Starting " + coin + " blockchain download ...");*/
        appKit.startAsync();
        appKit.awaitRunning();

        String coin2 = "BCH";
        NetworkParameters params2 = MainNetParams.get(); //BCCTestNet3Params.get(); //BCCMainNetParams.get();
        File dir2 = new File("BobAppCash");

        Context.propagate(new Context(params2));
        System.out.println("Creating new " + coin2 + " wallet ...");
        WalletAppKit appKit2 = new WalletAppKit(params2, dir2, "Bobby-BCH");

        //System.out.println("Adding progress tracker ...");
        //appKit2.setDownloadListener(new Tracker(coin2)).setBlockingStartup(false);

        System.out.println("Starting " + coin2 + " blockchain download ...");
        appKit2.startAsync();
        appKit2.awaitRunning();

        
        while (true) {
            assert (appKit != null);
            assert (appKit.wallet() != null);

            Integer lastHeight = -1;
            if (appKit.wallet() == null) {
                System.out.print("Waiting");
            } else {
                lastHeight = appKit.wallet().getLastBlockSeenHeight();
            }

            List peers = new LinkedList();
            if (appKit.peerGroup() == null) {
                System.out.print("Waiting");
            } else {
                peers = appKit.peerGroup().getConnectedPeers();
            }

            Integer lastHeight2 = -1;
            if (appKit2.wallet() == null) {
                System.out.print("Waiting");
            } else {
                lastHeight2 = appKit2.wallet().getLastBlockSeenHeight();
            }

            List peers2 = new LinkedList();
            if (appKit2.peerGroup() == null) {
                System.out.print("Waiting");
            } else {
                peers2 = appKit2.peerGroup().getConnectedPeers();
            }
            
            System.out.print("BTC Last height: "+lastHeight+" Balance: "+appKit.wallet().getBalance()+" Peers: "+peers.size());
            System.out.print("BCH Last height: "+lastHeight2+" Balance: "+appKit2.wallet().getBalance()+" Peers: "+peers2.size()+"\r");
        }
    }
}
