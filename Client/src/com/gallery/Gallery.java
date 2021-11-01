package com.gallery;

import javax.swing.*;
import java.awt.*;

public class Gallery {

    JPanel humanInfoPanel;
    JPanel animalInfoPanel;
    JPanel eventInfoPanel;
    JPanel picturePanel;

    public Gallery(JPanel galleryPanel){

        humanInfoPanel = new JPanel();
        new HumanInfo(humanInfoPanel);
        animalInfoPanel = new JPanel();
        new AnimalInfo(animalInfoPanel);
        eventInfoPanel = new JPanel();
        new EventInfo(eventInfoPanel);
        picturePanel = new JPanel();
        new PictureInfo(picturePanel);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.rowHeights = new int[]{250,225,325};
        gridBagLayout.columnWidths = new int[]{800,500};
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        galleryPanel.setLayout(gridBagLayout);

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        galleryPanel.add(humanInfoPanel, gridBagConstraints);

        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        galleryPanel.add(animalInfoPanel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        galleryPanel.add(eventInfoPanel, gridBagConstraints);

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        galleryPanel.add(picturePanel, gridBagConstraints);

    }
}
