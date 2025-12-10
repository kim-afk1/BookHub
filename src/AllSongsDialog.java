import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.List;
import javax.sound.sampled.*;
import java.io.IOException;

public class AllSongsDialog extends JDialog {
    public AllSongsDialog(Window owner, List<Song> songs) {
        super(owner, "All Songs", ModalityType.APPLICATION_MODAL);

        setSize(400, 500);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JList<Song> songList = new JList<>(songs.toArray(new Song[0]));
        songList.setFont(new Font("Arial", Font.PLAIN, 14));

        add(new JLabel("All Uploaded Songs (" + songs.size() + ")") {{
            setFont(new Font("Arial", Font.BOLD, 16));
            setBorder(new EmptyBorder(10, 10, 10, 10));
        }}, BorderLayout.NORTH);

        add(new JScrollPane(songList), BorderLayout.CENTER);

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());
        add(closeBtn, BorderLayout.SOUTH);
    }
}