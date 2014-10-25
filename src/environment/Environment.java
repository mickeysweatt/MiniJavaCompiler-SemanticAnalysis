package environment; /**
 * Created by Mickey Sweatt
 * 22 Oct 2014
 */

import java.util.HashMap;
import java.util.Map;

public class Environment<ENTITY_TYPE> {
    private Map<String, ENTITY_TYPE> m_entries;

   public Environment()
   {
       m_entries = null;
   }

    public Environment(Environment other)
    {
        if (null == other.getEntries())
        {
            m_entries = null;
            return;
        }

        m_entries = new HashMap<String, ENTITY_TYPE>();
        for (Object entry_name : other.getEntries().keySet())
        {
            m_entries.put((String) entry_name, (ENTITY_TYPE) other.getEntries().get(entry_name));
        }
    }

    public void addEntry(ENTITY_TYPE c)
    {
        if (null == m_entries) {
            m_entries = new HashMap<String, ENTITY_TYPE>();
        }
        m_entries.put(c.toString(), c);
    }

    public boolean containsEntry(String entry_name)
    {
        return null != getEntry(entry_name);
    }

    public ENTITY_TYPE getEntry(String entry_name)
    {
        if (null != m_entries) {
            return m_entries.get(entry_name);
        }
        return null;
    }

    public Map<String, ENTITY_TYPE> getEntries()
    {
        return m_entries;
    }


}


