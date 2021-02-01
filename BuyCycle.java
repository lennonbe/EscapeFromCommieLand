package BoringGame;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import java.util.concurrent.TimeUnit;

public class BuyCycle 
{
    Fields[] fieldArray;
    ResourceMenu resourceMenu;
    BuyMenu buyMenu; 
    RenderWindow window;
    Game game;

    public BuyCycle(Fields[] array, ResourceMenu resMenu, BuyMenu buyMenuInput, RenderWindow windowInput, Game gameInput)
    {
        fieldArray = array;
        resourceMenu = resMenu;
        buyMenu = buyMenuInput;
        window = windowInput;
        game = gameInput;
    }

    /**
     * Chooses what vegetable to buy.
     * @param window the current game window for prespective
     * @return the array index of the vegetables
     */
    public int buyVeg(float mouseX, float mouseY)
    {
        int returnValue = -1;
        int decrementValue = 0;

        if(mouseX >= buyMenu.vegIcons[0].getPosition().x && mouseX <= buyMenu.vegIcons[0].getPosition().x + buyMenu.vegIcons[0].getSize().x && mouseY >= buyMenu.vegIcons[0].getPosition().y && mouseY <= buyMenu.vegIcons[0].getPosition().y + buyMenu.vegIcons[0].getSize().y)
        {
            SoundEffect.PURCHASEITEM.play();
            returnValue = 0;
            decrementValue = 1;
        }
        else if(mouseX >= buyMenu.vegIcons[1].getPosition().x && mouseX <= buyMenu.vegIcons[1].getPosition().x + buyMenu.vegIcons[1].getSize().x && mouseY >= buyMenu.vegIcons[1].getPosition().y && mouseY <= buyMenu.vegIcons[1].getPosition().y + buyMenu.vegIcons[1].getSize().y)
        {
            SoundEffect.PURCHASEITEM.play();
            returnValue = 1;
            decrementValue = 2;
        }
        else if(mouseX >= buyMenu.vegIcons[2].getPosition().x && mouseX <= buyMenu.vegIcons[2].getPosition().x + buyMenu.vegIcons[2].getSize().x && mouseY >= buyMenu.vegIcons[2].getPosition().y && mouseY <= buyMenu.vegIcons[2].getPosition().y + buyMenu.vegIcons[2].getSize().y)
        {
            SoundEffect.PURCHASEITEM.play();
            returnValue = 2;
            decrementValue = 5;
        }
        else if(mouseX >= buyMenu.vegIcons[3].getPosition().x && mouseX <= buyMenu.vegIcons[3].getPosition().x + buyMenu.vegIcons[3].getSize().x && mouseY >= buyMenu.vegIcons[3].getPosition().y && mouseY <= buyMenu.vegIcons[3].getPosition().y + buyMenu.vegIcons[3].getSize().y)
        {
            SoundEffect.PURCHASEITEM.play();
            returnValue = 3;
            decrementValue = 7;
        }

        if(returnValue != -1 && buyMenu.menuOpen == true && decrementValue != 0)
        {
            if(resourceMenu.getIndexVal(4) >= decrementValue)
            {
                resourceMenu.increment(returnValue);
                resourceMenu.decrement(4, decrementValue);
            }
        }

        return returnValue;
    }

    /**
     * Sets the field that is currently selected to grow if a seed type is also currently selected.
     */
    public void selectVegToGrowOnField()
    {
        Fields temp = fieldArray[1].selectedField;
        if(temp != null && resourceMenu.getSelectedIndex() != -1 && temp != game.farmFields[12])
        {
            if(resourceMenu.getSelectedIndex() == 0)
            {
                SoundEffect.CROPPLANT.play();
                temp.setVegType(0);
            }
            else if(resourceMenu.getSelectedIndex() == 1)
            {
                SoundEffect.CROPPLANT.play();
                temp.setVegType(1);
            }
            else if(resourceMenu.getSelectedIndex() == 2)
            {
                SoundEffect.CROPPLANT.play();
                temp.setVegType(2);
            }
            else if(resourceMenu.getSelectedIndex() == 3)
            {
                SoundEffect.CROPPLANT.play();
                temp.setVegType(3);
            }

            //Unselects the curretnly selected to grow field
            if(fieldArray[1].getSelectedField().growing)
            {
                fieldArray[1].selectedField = null;
            }
        }
    }

    /**
     * Detects the clicks on the specific fields for collection.
     */
    public void collectVeg()
    {
        if(buyMenu.menuOpen == false)
        {
            for(int i = 0; i < fieldArray.length; i++)
            {
                if(fieldArray[i].isClicked(window) && i != 12) // cause field 12 is behind the shop TODO: Remove field12 safely
                {
                    if(fieldArray[i].readyToCollect == false)
                    {
                        pause();
                    }
                    else
                    {
                        //Algorithm for collecting veg from a specific field
                        if(fieldArray[i].getVegType() != "")
                        {
                            if(fieldArray[i].getVegType() == "Hemp")
                            {
                                SoundEffect.CROPHARVEST.play();
                                resourceMenu.increment(4, 15);
                            }
                            else if(fieldArray[i].getVegType() == "Chilli")
                            {
                                SoundEffect.CROPHARVEST.play();
                                resourceMenu.increment(4, 10);
                            }
                            else if(fieldArray[i].getVegType() == "Cauliflower")
                            {
                                SoundEffect.CROPHARVEST.play();
                                resourceMenu.increment(4, 5);
                            }
                            else if(fieldArray[i].getVegType() == "Carrot")
                            {
                                SoundEffect.CROPHARVEST.play();
                                resourceMenu.increment(4, 1);
                            }

                            fieldArray[i].setVegType("");
                            fieldArray[i].selectedField = null;
                            fieldArray[i].readyToCollect = false;
                        }

                        fieldArray[i].loadPathToRectangle("BoringGame", "DirtWet.png");
                        pause();
                    }
                }
            }
        }
    }

    /**
     * Slows down the game by sleeping for 1/4 of a second
     */
    public void pause()
    {
        try 
        {
            TimeUnit.MILLISECONDS.sleep(250); 
        } 
        catch (Exception e) 
        {

        }
    }
}