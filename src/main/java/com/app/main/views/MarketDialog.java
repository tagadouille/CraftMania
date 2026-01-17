/*package main.java.com.views;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.java.com.Screen;
import main.java.com.models.Market;
import main.java.com.models.Ressources.RessourceEnum;
import main.java.com.views.utilities.Background;
import main.java.com.views.utilities.ImageLoader;
import main.java.com.views.utilities.ItemImageEnum;

public class MarketDialog extends JDialog{
    private CardLayout card = new CardLayout();
    private JPanel mainPanel = new JPanel(card);

    private Market market; 

    public MarketDialog(Market market){
        super();
        this.setModal(true);
        this.setTitle("Market Screen");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(card);

        this.setSize(new Dimension(Screen.getScreenWidth()/2, Screen.getScreenHeight()/2));
        this.add(mainPanel);

        this.market = market;

        mainPanel.add(new BuyPanel(), "buyPanel");
        mainPanel.add(new SellPanel(), "sellPanel");


        JPanel homePage = new JPanel();
        JButton buy = new JButton("Buy");
        buy.addActionListener((e) -> card.show(mainPanel, "buyPanel"));
        JButton sell = new JButton("Sell");
        sell.addActionListener((e) -> card.show(mainPanel, "sellPanel"));

        homePage.add(buy);
        homePage.add(sell);
        mainPanel.add(homePage, "homePage");
        card.show(mainPanel, "homePage");
    }
    private class BuyPanel extends JPanel{

        public BuyPanel(){
            this.setLayout(new GridLayout(2,1));
            JLabel title = new JLabel("What do you want to buy ?");
            this.add(title);

            JButton back = new JButton("Back");
            back.addActionListener((e) -> {
                card.show(mainPanel, "homePage");
            });
            this.add(back);
        }
    }
    private class SellPanel extends JPanel{
        private String imagePath;
        private JPanel panel = new JPanel(new GridBagLayout());

        public SellPanel(){
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 0;

            JLabel title = new JLabel("What do you want to sell ?");
            panel.add(title, constraints);
            constraints.gridy++;

            displayRessources(constraints);
            constraints.gridy++;

            JButton back = new JButton("Back");
            back.addActionListener((e) -> {
                card.show(mainPanel, "homePage");
            });
            panel.add(back, constraints);

            this.add(panel);
        }
        private void displayRessources(GridBagConstraints constraints){
            JScrollPane scrollPane = new JScrollPane();
            
            JPanel ressourcePanel = new JPanel(new GridBagLayout());

            GridBagConstraints ressourceConstraints = new GridBagConstraints();
            ressourceConstraints.gridx = 0;
            ressourceConstraints.gridy = 0;

            for (RessourceEnum res : RessourceEnum.values()) {
                JPanel line = new JPanel(new GridBagLayout());

                GridBagConstraints lineConstraints = new GridBagConstraints();
                lineConstraints.gridx = 0;
                lineConstraints.gridy = 0;
                lineConstraints.gridheight = 2;
                lineConstraints.insets = new Insets(0, 0, 0, 10);

                //Affichage de l'image de la ressource
                imagePath = "";
                for(ItemImageEnum type : ItemImageEnum.values()){
                    if(res.getRessource().getName().equals(type.toString())){
                        imagePath = type.getImagePath();
                        break;
                    }
                }
                if(imagePath.equals("")){
                    return;
                }
                JPanel imagePanel = new Background(imagePath, new Dimension(50, 50));

                line.add(imagePanel, lineConstraints);
                lineConstraints.gridx++;

                JLabel resName = new JLabel(res.toString().toLowerCase());
                line.add(resName, lineConstraints);
                lineConstraints.gridy++;

                JLabel price = new JLabel(res.getRessource().getPrice() +"$");
                line.add(price, lineConstraints);
                lineConstraints.gridx++;

                JLabel number = new JLabel(market.getPlayer().numberRessourceInInventory(res) + " already in inventory");
                line.add(number, lineConstraints);
                lineConstraints.gridx++;

                ressourcePanel.add(line, ressourceConstraints);
                ressourceConstraints.gridy++;
            }
            scrollPane.setViewportView(ressourcePanel);
            panel.add(scrollPane, constraints);
            this.revalidate();
            this.repaint(); 
        }
    }
}*/
