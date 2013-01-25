/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.smartdashboard.properties;

/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class CharProperty extends TextInputProperty<Character>{
	public CharProperty(PropertyHolder element, String name) {
        super(Character.class, element, name);
    }

    public CharProperty(PropertyHolder element, String name, Character defaultValue) {
        super(Character.class, element, name, defaultValue);
    }

    @Override
    protected Character transformValue(Object value) {
        return value.toString().charAt(0);
    }
}
