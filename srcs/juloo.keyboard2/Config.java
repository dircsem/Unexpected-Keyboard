package juloo.keyboard2;

import android.content.res.Resources;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.TypedValue;

class Config
{
	private Keyboard2			_context;

	public final float			marginTop;
	public final float			keyPadding;
	public final float			keyVerticalInterval;
	public final float			keyHorizontalInterval;
	public final float			keyRound;

  public int layout; // Or '-1' for the system defaults
	public float				subValueDist;
	public boolean				vibrateEnabled;
	public long					vibrateDuration;
	public long					longPressTimeout;
	public long					longPressInterval;
	public float				marginBottom;
	public float				keyHeight;
	public float				horizontalMargin;
  public boolean      disableAccentKeys;
  public boolean preciseRepeat;
  public float characterSize; // Ratio

  public boolean shouldOfferSwitchingToNextInputMethod;

	public Config(Keyboard2 context)
	{
		Resources			res = context.getResources();

		_context = context;
		// static values
		marginTop = res.getDimension(R.dimen.margin_top);
		keyPadding = res.getDimension(R.dimen.key_padding);
		keyVerticalInterval = res.getDimension(R.dimen.key_vertical_interval);
		keyHorizontalInterval = res.getDimension(R.dimen.key_horizontal_interval);
		keyRound = res.getDimension(R.dimen.key_round);
		// default values
    layout = -1;
		subValueDist = 10f;
		vibrateEnabled = true;
		vibrateDuration = 20;
		longPressTimeout = 600;
		longPressInterval = 65;
		marginBottom = res.getDimension(R.dimen.margin_bottom);
		keyHeight = res.getDimension(R.dimen.key_height);
		horizontalMargin = res.getDimension(R.dimen.horizontal_margin);
    disableAccentKeys = false;
    preciseRepeat = true;
    characterSize = 1.f;
		// from prefs
		refresh();
    // initialized later
    shouldOfferSwitchingToNextInputMethod = false;
	}

	/*
	** Reload prefs
	*/
	public void			refresh()
	{
		SharedPreferences	prefs = PreferenceManager.getDefaultSharedPreferences(_context);

    layout = layoutId_of_string(prefs.getString("layout", "system")); 
		subValueDist = prefs.getFloat("sub_value_dist", subValueDist);
		vibrateEnabled = prefs.getBoolean("vibrate_enabled", vibrateEnabled);
		vibrateDuration = prefs.getInt("vibrate_duration", (int)vibrateDuration);
		longPressTimeout = prefs.getInt("longpress_timeout", (int)longPressTimeout);
		longPressInterval = prefs.getInt("longpress_interval", (int)longPressInterval);
		marginBottom = getDipPref(prefs, "margin_bottom", marginBottom);
		keyHeight = getDipPref(prefs, "key_height", keyHeight);
		horizontalMargin = getDipPref(prefs, "horizontal_margin", horizontalMargin);
    disableAccentKeys = prefs.getBoolean("disable_accent_keys", disableAccentKeys);
    preciseRepeat = prefs.getBoolean("precise_repeat", preciseRepeat);
    characterSize = prefs.getFloat("character_size", characterSize); 
	}

	private float		getDipPref(SharedPreferences prefs, String pref_name, float def)
	{
		int					value = prefs.getInt(pref_name, -1);

		if (value < 0)
			return (def);
		return (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
			_context.getResources().getDisplayMetrics()));
	}

  public static int layoutId_of_string(String name)
  {
    switch (name)
    {
      case "azerty": return R.xml.azerty;
      case "qwerty": return R.xml.qwerty;
      case "system": return -1;
      default: throw new IllegalArgumentException();
    }
  }

}
