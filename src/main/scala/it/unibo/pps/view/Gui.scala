package it.unibo.pps.view

import javax.swing.{JButton, JFrame, JLabel, JPanel, SwingUtilities}
import java.awt.{BorderLayout, Canvas, Color, Dimension, Graphics}
import java.awt.event.{ActionEvent, ActionListener}
import it.unibo.pps.controller.ControllerModule
import it.unibo.pps.model.body.Body

class Gui(width: Int, height: Int, controller: ControllerModule.Controller):
  self =>
  private val frame = JFrame()
  private val canvas = Environment()
  private val btnPanel = JPanel()
  private val layout = BorderLayout()
  private val startButton = new JButton("Start")
  frame.setSize(width+200, height+100)
  frame.setLocationRelativeTo(null)
  canvas.setSize(width, height)
  btnPanel.add(startButton)
  startButton.addActionListener((e:ActionEvent) => controller.notifyStart())
  frame.setLayout(layout)
  frame.add(canvas, BorderLayout.NORTH)
  frame.add(btnPanel, BorderLayout.SOUTH)
  canvas.setVisible(true)
  frame.setVisible(true)

  def render(b: Body): Unit = SwingUtilities.invokeLater{ () =>
    canvas.elementPos = b.position
    canvas.invalidate()
    canvas.repaint()
  }

  private class Environment() extends JPanel:

    var elementPos = (0.0, 0.0)

    override def getPreferredSize = new Dimension(self.width, self.height)
    override def paintComponent(graphics: Graphics): Unit =
      graphics.setColor(Color.BLACK)
      graphics.drawOval(elementPos._1.toInt, elementPos._2.toInt, 10, 10)
