package goedegep.app.finan.overzichten;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import goedegep.app.finan.gen.FinanBank;
import goedegep.appgen.CheckBoxTreeCellEditor;
import goedegep.appgen.CheckBoxTreeCellRenderer;
import goedegep.appgen.CheckBoxTreeNodeUserObjectWrapper;
import goedegep.appgen.ConfigurableTreeCellEditor;
import goedegep.appgen.ConfigurableTreeCellRenderer;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.finan.basic.Bank;
import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.SumAccount;

/**
 * Een JSplitPane met de vermogensontwikkeling.
 * De linker helft is het controle paneel met:
 * - bovenin: radio buttons om het koers type te selecteren (absoluut of genormalizeerd).
 * - onderin: de maatsschappijen met hun fondsen, weergegeven in een boom.
 * De grafiek, rechts, geeft de koersen van de geselecteerde fondsen weer en met het geselecteerde koers type.
 */
@SuppressWarnings("serial")
public class VermogensontwikkelingsGrafiekPanel extends JSplitPane {
  private final static Logger LOGGER = Logger.getLogger(VermogensontwikkelingsGrafiekPanel.class.getName());

  private ComponentFactory componentFactory;
  private VermogensontwikkelingsGrafiek vermogensontwikkelingsGrafiek;
  private JRadioButton bankButton;
  private JTree tree;
  private DefaultTreeModel rekeningenModel;
  private DefaultTreeModel bankenModel;
  //  private JPanel controlPanel;

  VermogensontwikkelingsGrafiekPanel(AppFrame owner, String title, SumAccount sumAccount) {
    componentFactory = owner.getTheComponentFactory();

    vermogensontwikkelingsGrafiek = new VermogensontwikkelingsGrafiek(owner, title);
    Component controlPanel = createControlPanel(sumAccount);

    tree.getModel().addTreeModelListener(new TreeModelListener() {

      @Override
      public void treeNodesChanged(TreeModelEvent e) {
        updateFondsOntwikkelingsGrafiek();
      }

      @Override
      public void treeNodesInserted(TreeModelEvent e) {
        // Cannot happen, so no action.
      }

      @Override
      public void treeNodesRemoved(TreeModelEvent e) {
        // Cannot happen, so no action.
      }

      @Override
      public void treeStructureChanged(TreeModelEvent e) {
        // Cannot happen, so no action.
      }

    });

    updateFondsOntwikkelingsGrafiek();

    setLeftComponent(controlPanel);
    setRightComponent(vermogensontwikkelingsGrafiek);
    setOrientation(JSplitPane.HORIZONTAL_SPLIT);
  }

  private JPanel createControlPanel(SumAccount sumAccount) {
    JPanel controlPanel = componentFactory.createPanel(-1, -1, false);

    controlPanel.setLayout(new BorderLayout());

    Component modePanel = createModePanel();
    controlPanel.add(modePanel, BorderLayout.CENTER);

    Component selectionTreePanel = createSelectionTreePanel(sumAccount);
    controlPanel.add(selectionTreePanel, BorderLayout.SOUTH);

    return controlPanel;

    //    boolean bankenMode = false; // start in rekeningen mode.
    //    JPanel controlPanel = componentFactory.createPanel(300, 800, false);
    //    
    //    controlPanel.add(new ModePanel(owner, bankenMode), BorderLayout.NORTH);
    //    
    //    selectionTreePanel = new SelectionTreePanel(sumAccount, bankenMode);
    ////    selectionTreePanel.addListener(new SelectionHandler());
    //    controlPanel.add(selectionTreePanel.getSP(), BorderLayout.SOUTH);
    //    
    //    return controlPanel;
  }

  private Component createModePanel() {
    JPanel buttonPanel = componentFactory.createPanel(-1, -1, false);
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    buttonPanel.setBorder(new TitledBorder("Vertoon informatie per"));

    JLabel koersLabel = componentFactory.createLabel("Koers", SwingConstants.LEFT);
    buttonPanel.add(koersLabel);

    ButtonGroup  levelGroup = new ButtonGroup();

    bankButton = componentFactory.createRadioButton("Bank", true);
    buttonPanel.add(bankButton);
    levelGroup.add(bankButton);      
    bankButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        updateTreeModel();      
      }

    });

    JRadioButton rekeningButton = componentFactory.createRadioButton("Rekening", true);
    buttonPanel.add(rekeningButton);
    levelGroup.add(rekeningButton);
    rekeningButton.setSelected(true);
    rekeningButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        updateTreeModel();      
      }

    });

    return buttonPanel;    
  }

  private boolean getBankenMode() {
    // As there are just 2 options, of which always 1 is selected, we only have to look at the status of 1 button.
    return bankButton.isSelected();
  }

  private void updateTreeModel() {
    if (getBankenMode()) {
      tree.setModel(bankenModel);
    } else {
      tree.setModel(rekeningenModel);
    }

  }

  private Component createSelectionTreePanel(SumAccount sumAccount) {
    LOGGER.severe("=>");

    DefaultMutableTreeNode rekeningenRootNode = new DefaultMutableTreeNode("Rekeningen");
    DefaultMutableTreeNode bankenRootNode = new DefaultMutableTreeNode("Banken");
    rekeningenModel = new DefaultTreeModel(rekeningenRootNode);
    bankenModel = new DefaultTreeModel(bankenRootNode);

    for (FinanBank bank: sumAccount.getBanks()) {
      DefaultMutableTreeNode rekeningenBankNode = new DefaultMutableTreeNode(bank);
      DefaultMutableTreeNode bankBankNode = new DefaultMutableTreeNode(new CheckBoxTreeNodeUserObjectWrapper(bank));
      rekeningenRootNode.add(rekeningenBankNode);
      bankenRootNode.add(bankBankNode);
      for(PgAccount account: bank.getBank().getAccounts()) {
        DefaultMutableTreeNode accountNode = new DefaultMutableTreeNode(new CheckBoxTreeNodeUserObjectWrapper(new AccountWrapper(account)));
        rekeningenBankNode.add(accountNode);
      }
    }

    if (getBankenMode()) {
      tree = new JTree(bankenModel);
    } else {
      tree = new JTree(rekeningenModel);
    }
    
    tree.setRootVisible(true);
    ConfigurableTreeCellRenderer cellRenderer = new ConfigurableTreeCellRenderer();
    cellRenderer.addClassSpecificCellRenderer(CheckBoxTreeNodeUserObjectWrapper.class, new CheckBoxTreeCellRenderer());
    tree.setCellRenderer(cellRenderer);

    ConfigurableTreeCellEditor cellEditor = new ConfigurableTreeCellEditor(tree, new DefaultTreeCellRenderer());
    cellEditor.addClassSpecificCellEditor(CheckBoxTreeNodeUserObjectWrapper.class, new CheckBoxTreeCellEditor(tree, cellEditor));
    tree.setCellEditor(cellEditor);
    tree.setEditable(true);
    JScrollPane selectionTreePanel = new JScrollPane(tree);
    LOGGER.severe("<=");

    return selectionTreePanel;

    //    tree.addMouseListener(new NodeSelectionListener(tree));
    //    tree.setBackground(look.getAchtergrondKleur());
    //    tree.setSize(300, 500);
    //    selectionTreePanel = new JScrollPane(tree);
  }

  List<Object> getSelection() {
    if (getBankenMode()) {
      return getSelectedBanks();
    } else {
      return getSelectedAccounts();
    }
  }

  List<Object> getSelectedBanks() {
    List<Object> selectedBanks = new ArrayList<Object>();
    DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();

    @SuppressWarnings("rawtypes")
    Enumeration bankNodes = root.children();
    while (bankNodes.hasMoreElements()) {
      DefaultMutableTreeNode bankNode = (DefaultMutableTreeNode) bankNodes.nextElement();
      CheckBoxTreeNodeUserObjectWrapper userObjectWrapper = (CheckBoxTreeNodeUserObjectWrapper) bankNode.getUserObject();
      if (userObjectWrapper.isSelected()) {
        selectedBanks.add((Bank) userObjectWrapper.getUserObject());
      }
    }

    return selectedBanks;
  }

  List<Object> getSelectedAccounts() {
    List<Object> selectedAccounts = new ArrayList<Object>();
    DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();

    @SuppressWarnings("rawtypes")
    Enumeration bankNodes = root.children();
    while (bankNodes.hasMoreElements()) {
      DefaultMutableTreeNode bankNode = (DefaultMutableTreeNode) bankNodes.nextElement();
      @SuppressWarnings("rawtypes")
      Enumeration accountNodes = bankNode.children();
      while (accountNodes.hasMoreElements()) {
        DefaultMutableTreeNode accountNode = (DefaultMutableTreeNode) accountNodes.nextElement();
        CheckBoxTreeNodeUserObjectWrapper userObjectWrapper = (CheckBoxTreeNodeUserObjectWrapper) accountNode.getUserObject();
        if (userObjectWrapper.isSelected()) {
          selectedAccounts.add(((AccountWrapper) userObjectWrapper.getUserObject()).getAccount());
        }
      }
    }

    return selectedAccounts;
  }

  private void updateFondsOntwikkelingsGrafiek() {
      vermogensontwikkelingsGrafiek.changeSettings(getSelection());
  }

  private class AccountWrapper {
    PgAccount   account;

    public AccountWrapper(PgAccount account) {
      this.account = account;
    }

    protected PgAccount getAccount() {
      return account;
    }

    public String toString() {
      return account.getName();
    }
  }
}


