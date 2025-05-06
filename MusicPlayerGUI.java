import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayerGUI extends JFrame implements ActionListener {

    JButton playButton, stopButton, resumeButton, restartButton, browseButton;
    JLabel songLabel;

    Clip clip; // It allows operations like start(), stop(), etc.
    File currentFile;

    public MusicPlayerGUI() {
        setTitle("Java Music Player");
        setSize(400, 250);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        songLabel = new JLabel("No song selected");
        songLabel.setPreferredSize(new Dimension(350, 30));
        songLabel.setBackground(new Color(169, 169, 169)); // Corrected: moved into the constructor
        songLabel.setOpaque(true); // You need to set it opaque for background color to be visible
        songLabel.setFont(new Font("Arial", Font.BOLD, 17));
		
         browseButton = new JButton("Browse & Play");
		 browseButton.setBackground(new Color(0, 123, 255));
         browseButton.setOpaque(true); 
		
         playButton = new JButton("Play Again");
		 playButton.setBackground(new Color(40,167,69));
         playButton.setOpaque(true); 
		
        stopButton = new JButton("Stop");
		stopButton.setBackground(new Color(220, 53, 69)); 
        stopButton.setOpaque(true);
		
        resumeButton = new JButton("Resume");
		resumeButton.setBackground(new Color(40, 167,69));
        resumeButton.setOpaque(true);
		
        restartButton = new JButton("Restart");
		restartButton.setBackground(new Color(253, 126, 20));
        restartButton.setOpaque(true); 

        add(songLabel);
        add(browseButton);
        add(playButton);
        add(stopButton);
        add(resumeButton);
        add(restartButton);

        browseButton.addActionListener(this);
        playButton.addActionListener(this);
        stopButton.addActionListener(this);
        resumeButton.addActionListener(this);
        restartButton.addActionListener(this);

        setVisible(true);
    }

    public void playMusic(File file) {
        try {
            if (clip != null && clip.isOpen()) clip.close();

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            currentFile = file;
            songLabel.setText("Playing: " + file.getName());

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void resumeMusic() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }

    public void restartMusic() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == browseButton) {
            JFileChooser chooser = new JFileChooser();
            int option = chooser.showOpenDialog(this);

            if (option == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                playMusic(file);
            }

        } else if (source == playButton) {
            if (currentFile != null) playMusic(currentFile);
        } else if (source == stopButton) {
            stopMusic();
        } else if (source == resumeButton) {
            resumeMusic();
        } else if (source == restartButton) {
            restartMusic();
        }
    }

    public static void main(String[] args) {
        new MusicPlayerGUI();
    }
}
