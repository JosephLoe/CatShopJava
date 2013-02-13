package Clients;

import java.awt.*;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import Middle.*;

import java.util.*;
import java.io.*;

/**
 * Implements the GUI for the Display client.
 * @author  Michael Alexander Smith
 * @version 2.1
 */

class DisplayGUI
{
  public DisplayGUI(   RootPaneContainer rpc, MiddleFactory mf  )
  {
    ModelOfDisplay    model = new ModelOfDisplay( mf );
    ViewOfDisplay     view  = new ViewOfDisplay( rpc );
   
    model.addObserver( view );            // Add observer to the model  
    Thread life = new Thread( model );    // Initial display  
    life.start();
  }
}